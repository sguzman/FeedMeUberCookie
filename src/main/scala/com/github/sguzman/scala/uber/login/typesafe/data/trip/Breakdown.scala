package com.github.sguzman.scala.uber.login.typesafe.data.trip

case class Breakdown(
                    category: String,
                    items: Array[Item],
                    total: String,
                    description: String
                    )
