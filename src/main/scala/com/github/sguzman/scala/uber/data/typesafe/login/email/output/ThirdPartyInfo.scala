package com.github.sguzman.scala.uber.data.typesafe.login.email.output

case class ThirdPartyInfo(
                         email: String,
                         firstName: String,
                         identityTypes: Option[String],
                         lastName: String
                         )
