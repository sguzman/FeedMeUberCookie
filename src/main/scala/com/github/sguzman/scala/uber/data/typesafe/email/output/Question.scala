package com.github.sguzman.scala.uber.data.typesafe.email.output

case class Question(
                   identifiedUserInfo: IdentifiedUserInfo,
                   signinToken: String,
                   thirdPartyInfo: ThirdPartyInfo,
                   tripChallenges: Array[String],
                   `type`: String
                   )
