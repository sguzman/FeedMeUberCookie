package com.github.sguzman.scala.uber.login.typesafe.data.statement

import java.util.UUID

case class TripChaining(
                       chain_index: Option[String],
                       normalized_distance: Double,
                       chain_uuid: UUID,
                       normalized_duration: Option[Long]
                       )
