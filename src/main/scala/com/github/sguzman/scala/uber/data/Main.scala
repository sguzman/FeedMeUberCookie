package com.github.sguzman.scala.uber.data

import java.net.{SocketTimeoutException, URI}

import com.github.sguzman.scala.uber.data.typesafe.data.all_data.AllDataStatement
import com.github.sguzman.scala.uber.data.typesafe.data.statement.Statement
import com.github.sguzman.scala.uber.data.typesafe.data.trip.{Trip, Trip2}
import com.github.sguzman.scala.uber.data.typesafe.verify.PlatformChromeNavData
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import lol.http.{Server, _}
import org.feijoas.mango.common.base.Preconditions

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalaj.http.Http

object Main {
  val cache: mutable.Map[String, String] = mutable.Map[String, String]()
  val statementCache : mutable.Map[String, String] = mutable.Map[String, String]()
  val tripCache : mutable.Map[String, String] = mutable.Map[String, String]()

  def main(args: Array[String]): Unit = {
    Server.listen(util.Try(System.getenv("PORT").toInt) match {
      case Success(v) => v
      case Failure(_) => 8888
    }) {
      case GET at url"/hello" =>
        Ok("hello")
      case request @ GET at url"/" =>
        util.Try({
          val cookie = request.headers(HttpString("X-Cookies")).toString
          val content = if (this.cache.contains(cookie)) {
            println("Have seen cookie before... retrieving from cache")
            this.cache(cookie)
          } else {
            val dt = data(cookie)
            this.cache.put(cookie, dt)
            dt
          }

          Ok(content).addHeaders(
            (HttpString("Access-Control-Allow-Origin"), HttpString("*")),
            (HttpString("Access-Control-Allow-Headers"), HttpString("Origin, X-Requested-With, Content-Type, Accept"))
          )
        }) match {
          case Success(v) => v
          case Failure(e) =>
            e.printStackTrace()
            InternalServerError(e.toString).addHeaders(
              (HttpString("Access-Control-Allow-Origin"), HttpString("*")),
              (HttpString("Access-Control-Allow-Headers"), HttpString("Origin, X-Requested-With, Content-Type, Accept"))
            )
        }

      case _ =>
        NotFound.addHeaders(
          (HttpString("Access-Control-Allow-Origin"), HttpString("*")),
          (HttpString("Access-Control-Allow-Headers"), HttpString("Origin, X-Requested-With, Content-Type, Accept"))
        )
    }
  }

  def data(cookies: String): String = {
    assertCookie(cookies)
    val statement = getStatement(cookies, _: String)
    val trip = getTrip(cookies, _: String)

    val allData = getAllData(cookies)
    val statementPreviews = allData
      .par
      .map(_.uuid)
      .map(_.toString)
      .map(statement)
      .flatMap(_.body.driver.trip_earnings.trips.keySet.toList)
      .map(_.toString)
      .map(trip)
      .filter(_.isDefined)
      .toArray

    val map = Map("items" -> statementPreviews)
    val mapStr = map.asJson.toString
    mapStr
  }

  def assertCookie(cookies: String): Unit = {
    val url = "https://partners.uber.com/p3/platform_chrome_nav_data"
    val checkRequest = Http(url).header("Cookie", cookies)
    val checkResponse = checkRequest.asString

    val checkBody = checkResponse.body
    val checkObj = decode[PlatformChromeNavData](checkBody)
    Preconditions.checkArgument(checkObj.isRight, "Cookie failed validation")
  }

  def getAllData(cookies: String): Array[AllDataStatement] = {
    val allDataUrl = "https://partners.uber.com/p3/money/statements/all_data/"
    val allDataRequest = Http(allDataUrl).header("Cookie", cookies)
    val allDataResponse = allDataRequest.asString

    val allDataBody = allDataResponse.body
    val allDataObj = decode[Array[AllDataStatement]](allDataBody)
    Preconditions.checkArgument(allDataObj.isRight, "Failed validating all_data request")

    allDataObj.right.get
  }

  def getStatement(cookies: String, uuid: String): Statement = util.Try({
    val url = s"https://partners.uber.com/p3/money/statements/view/$uuid"
    val request = Http(url).header("Cookie", cookies)
    val response = request.asString

    if (response.code == 429) {
      getStatement(cookies, uuid)
    }
    else {
      println(s"Success $url")
      val body = decode[Statement](response.body)
      Preconditions.checkArgument(body.isRight, "Failed validating statements")
      body.right.get
    }
  }) match {
    case Success(v) => v
    case Failure(e) => e match {
      case _: SocketTimeoutException => getStatement(cookies, uuid)
    }
  }

  def getTrip(cookies: String, uuid: String): Option[Trip2] = util.Try({
    val url = s"https://partners.uber.com/p3/money/trips/trip_data/$uuid"
    val request = Http(url).header("Cookie", cookies)
    val response = request.asString

    if (response.code == 429) {
      getTrip(cookies, uuid)
    } else {
      println(s"Success $url")
      val body = decode[Trip](response.body)
      Preconditions.checkArgument(body.isRight, s"Failed validating trips: $body")
      util.Try({
        val latLng = new URI(
          body
          .right
          .get
          .customRouteMap
          .get
        )
          .getQuery
          .split("&")
          .filter(t => t.startsWith("markers="))
          .head
          .split("\\|")
          .last
          .split(",")
          .map(_.toDouble)


        Trip2(Some(latLng.head), Some(latLng.last), body.right.get)
      }) match {
        case Success(v) => Some(v)
        case Failure(_) => Some(Trip2(None, None, body.right.get))
      }
    }
  }) match {
    case Success(v) => v
    case Failure(e) => e match {
      case _: SocketTimeoutException => getTrip(cookies, uuid)
    }
  }
}
