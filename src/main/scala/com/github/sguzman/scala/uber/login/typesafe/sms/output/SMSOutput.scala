package com.github.sguzman.scala.uber.login.typesafe.sms.output

import java.util.UUID

case class SMSOutput(
                    nextURL: String,
                    stage: SMSOutputStage,
                    userUUID: UUID,
                    isSignup: Option[Boolean]
                    )
