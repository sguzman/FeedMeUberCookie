package com.github.sguzman.scala.uber.data.typesafe.data.trip

case class Breakdown(
                    category: String,
                    items: Array[Item],
                    total: String,
                    description: String
                    )
