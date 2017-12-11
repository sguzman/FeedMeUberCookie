package com.github.sguzman.scala.uber.data

import org.feijoas.mango.common.base.Preconditions

import scala.util.{Failure, Success}
import scalaj.http.Http

object Main {
  def main(args: Array[String]): Unit = {
    util.Try({
      //val response = Login.apply

      val cookies = System.getenv("COOKIES")
      Preconditions.checkNotNull(cookies)

      val url = "https://partners.uber.com/p3/platform_chrome_nav_data"
      println(Http(url).header("Cookie", cookies).asString)
    }) match {
      case Success(_) => println("Done")
      case Failure(e) => Console.err.println(e)
    }
  }
}
