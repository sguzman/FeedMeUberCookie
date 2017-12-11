package com.github.sguzman.scala.uber.data.typesafe.email.output

case class ThirdPartyInfo(
                         email: String,
                         firstName: String,
                         identityTypes: Option[String],
                         lastName: String
                         )
