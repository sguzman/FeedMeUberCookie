package com.github.sguzman.scala.uber.login.typesafe.data.statement

case class Statement(
                    statusCode: Int,
                    body: Body,
                    headers: Map[String,String],
                    request: Request,

                    )
