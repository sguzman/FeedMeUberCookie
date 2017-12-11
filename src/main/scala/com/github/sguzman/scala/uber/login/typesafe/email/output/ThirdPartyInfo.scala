package com.github.sguzman.scala.uber.login.typesafe.email.output

case class ThirdPartyInfo(
                         email: String,
                         firstName: String,
                         identityTypes: Option[String],
                         lastName: String
                         )
