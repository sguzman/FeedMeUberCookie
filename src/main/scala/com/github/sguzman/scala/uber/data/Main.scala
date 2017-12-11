package com.github.sguzman.scala.uber.data

import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {
    util.Try({
      val response = Login

    }) match {
      case Success(_) => println("Done")
      case Failure(e) => Console.err.println(e)
    }
  }
}
