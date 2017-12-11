package com.github.sguzman.scala.uber.login.typesafe.data.statement

case class Request(
                  uri: URI,
                  method: String,
                  headers: Map[String,String]
                  )
