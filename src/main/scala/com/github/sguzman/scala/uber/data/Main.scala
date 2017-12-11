package com.github.sguzman.scala.uber.data

import com.github.sguzman.scala.uber.data.typesafe.email.input.{Answer, Email, UserIdentifier}
import com.github.sguzman.scala.uber.data.typesafe.email.output.PostResponse
import com.github.sguzman.scala.uber.data.typesafe.password
import com.github.sguzman.scala.uber.data.typesafe.password.Password
import com.github.sguzman.scala.uber.data.typesafe.sms.input.SMS
import com.github.sguzman.scala.uber.data.typesafe.sms.output.SMSOutput
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import org.feijoas.mango.common.base.Preconditions

import scala.io.StdIn
import scala.util.{Failure, Success}
import scalaj.http.{Http, HttpResponse}

object Main {
  def main(args: Array[String]): Unit = {
    util.Try({
      val (user, pass) = getCreds
      val responseLoginPage = getLoginPage

      val responsePostEmail = postEmail(responseLoginPage, user)
      println(responsePostEmail.body)
      println(decode[PostResponse](responsePostEmail.body))

      val responsePostPass = postPassword(responsePostEmail, pass)
      println(responsePostPass.body)
      println(decode[PostResponse](responsePostPass.body))

      val sms = StdIn.readLine("Enter SMS: ")
      val responsePostSMS = postSMS(responsePostPass, responsePostEmail.cookies.mkString("; "), sms)
      println(responsePostSMS.statusLine)
      println(responsePostSMS.body)
      println(decode[SMSOutput](responsePostSMS.body))
    }) match {
      case Success(_) => println("Done")
      case Failure(e) => Console.err.println(e)
    }
  }

  def getLoginPage: HttpResponse[String] =
    Http("https://auth.uber.com/login/?next_url=https%3A%2F%2Fpartners.uber.com")
      .asString

  def getCreds: (String, String) = {
    val user = System.getenv("USERNAME")
    val pass = System.getenv("PASSWORD")

    Preconditions.checkNotNull(user)
    Preconditions.checkNotNull(pass)
    (user, pass)
  }

  def postEmail(response: HttpResponse[String], user: String): HttpResponse[String] = {
    val postURL = "https://auth.uber.com/login/handleanswer"
    val payload = Email(Answer(`type` = "VERIFY_INPUT_USERNAME", UserIdentifier(user)),  init = true)

    val emailBody = payload.asJson.toString
    val requestEmail = Http(postURL)
      .postData(emailBody)
      .header("Cookie", response.cookies.mkString("; "))
      .header("x-csrf-token", response.header("x-csrf-token").get)
      .header("Content-Type", "application/json")
    val responseEmail =  requestEmail.asString
    responseEmail
  }

  def postPassword(response: HttpResponse[String], pass: String): HttpResponse[String] = {
    val postURL = "https://auth.uber.com/login/handleanswer"
    val payload = Password(password.Answer(pass, "VERIFY_PASSWORD"), rememberMe = true)

    val passBody = payload.asJson.toString
    val requestPass = Http(postURL)
      .postData(passBody)
      .header("Cookie", response.cookies.mkString("; "))
      .header("x-csrf-token", response.header("x-csrf-token").get)
      .header("Content-Type", "application/json")
    val responsePass = requestPass.asString
    responsePass
  }

  def postSMS(response: HttpResponse[String], cookies: String, smsMsg: String): HttpResponse[String] = {
    val postURL = "https://auth.uber.com/login/handleanswer"
    val payload = SMS(typesafe.sms.input.Answer(smsMsg, "SMS_OTP"))

    val smsBody = payload.asJson.toString
    val requestSMS = Http(postURL)
      .postData(smsBody)
      .header("Cookie", cookies)
      .header("x-csrf-token", response.header("x-csrf-token").get)
      .header("Content-Type", "application/json")
    val responseSMS = requestSMS.asString
    responseSMS
  }
}
