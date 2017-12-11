package com.github.sguzman.scala.uber.data

import com.github.sguzman.scala.uber.data.typesafe.data.all_data.AllDataStatement
import com.github.sguzman.scala.uber.data.typesafe.verify.PlatformChromeNavData
import org.feijoas.mango.common.base.Preconditions

import scala.util.{Failure, Success}
import scalaj.http.Http
import io.circe.parser.decode
import io.circe.generic.auto._

object Main {
  def main(args: Array[String]): Unit = {
    util.Try({
      //val response = Login.apply

      val cookies = System.getenv("COOKIES")
      Preconditions.checkNotNull(cookies)

      val url = "https://partners.uber.com/p3/platform_chrome_nav_data"
      val checkRequest = Http(url).header("Cookie", cookies)
      val checkResponse = checkRequest.asString

      val checkBody = checkResponse.body
      println(checkBody)

      val checkObj = decode[PlatformChromeNavData](checkBody)
      Preconditions.checkArgument(checkObj.isRight)
      println(checkObj.right.get)

      val allDataUrl = "https://partners.uber.com/p3/money/statements/all_data/"
      val allDataRequest = Http(allDataUrl).header("Cookie", cookies)
      val allDataResponse = allDataRequest.asString

      val allDataBody = allDataResponse.body
      val allDataObj = decode[Array[AllDataStatement]](allDataBody)
      Preconditions.checkArgument(allDataObj.isRight)
      println(allDataObj.right.get)
    }) match {
      case Success(_) => println("Done")
      case Failure(e) => Console.err.println(e)
    }
  }
}
