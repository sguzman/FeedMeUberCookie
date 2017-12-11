package com.github.sguzman.scala.uber.data.typesafe.data.statement

import java.util.UUID

case class TripEarnings2(
                        trip_dates: Array[TripDate],
                        eligible_earnings: String,
                        cash_collected: String,
                        total_earned: String,
                        fee_types: FeeTypes,
                        totals: Totals,
                        rates: Option[String],
                        total: String,
                        trips: Map[UUID, Trip]
                        )
