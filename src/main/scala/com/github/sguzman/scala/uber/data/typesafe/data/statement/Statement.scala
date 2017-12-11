package com.github.sguzman.scala.uber.data.typesafe.data.statement

case class Statement(
                    statusCode: Int,
                    body: Body,
                    headers: Map[String,String],
                    request: Request,

                    )
