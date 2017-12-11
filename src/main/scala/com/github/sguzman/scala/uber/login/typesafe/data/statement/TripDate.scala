package com.github.sguzman.scala.uber.login.typesafe.data.statement

import java.util.UUID

case class TripDate(
                   date: String,
                   cash_collected_total: String,
                   total: String,
                   total_earned: String,
                   trips: Array[UUID]
                   )
