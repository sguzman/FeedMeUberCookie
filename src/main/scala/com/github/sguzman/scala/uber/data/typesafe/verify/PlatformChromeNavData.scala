package com.github.sguzman.scala.uber.data.typesafe.verify

case class PlatformChromeNavData(
                                avatars: Avatars,
                                languages: Array[Languages],
                                locale: Locale,
                                nav: Nav,
                                rating: Rating,
                                traslation: Map[String,String],
                                user: User
                                )
