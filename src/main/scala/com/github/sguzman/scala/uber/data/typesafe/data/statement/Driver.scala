package com.github.sguzman.scala.uber.data.typesafe.data.statement

import java.util.UUID

case class Driver(
                 contact_number: String,
                 cash_collected: String,
                 misc_fees_breakdown: Option[String],
                 trip_earnings: TripEarnings2,
                 driver_summary_stats: DriverSummaryStats,
                 total_earned: String,
                 first_name: String,
                 email: String,
                 last_name: String,
                 misc_non_fee_breakdown: Option[MiscNonFeeBreakdown],
                 driver_uuid: UUID,
                 total: String,
                 picture_url: String,
                 earnings: Earnings
                 )
