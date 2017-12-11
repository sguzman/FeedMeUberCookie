package com.github.sguzman.scala.uber.data.typesafe.verify

case class Rating(
                 aggregateType: String,
                 displayValue: String,
                 histogram: Map[Int, Int],
                 subject: Subject
                 )
