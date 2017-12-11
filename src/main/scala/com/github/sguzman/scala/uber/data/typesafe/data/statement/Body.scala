package com.github.sguzman.scala.uber.data.typesafe.data.statement

case class Body(
               city: City,
               driver_summary_stats: DriverSummaryStats,
               misc_fees_breakdown: Option[String],
               starting_at: String,
               driver: Driver,
               cash_collected: String,
               total_earned: String,
               cash_summary: Option[String],
               misc_non_fee_breakdowns: Option[MiscNonFeeBreakdown],
               trip_earnings: TripEarnings,
               partner: Partner,
               ending_at: String,
               total: String,
               currency_code: String
               )
