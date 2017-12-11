package com.github.sguzman.scala.uber.login.typesafe.data.all_data

import java.util.UUID

case class AllDataStatement(
                           partner_uuid: UUID,
                           status: String,
                           city_id: Int,
                           total: String,
                           id: Option[Int],
                           payout_type: Option[String],
                           payee_type: String,
                           uuid: UUID,
                           currency_code: String,
                           ending_at: Long,
                           num_txns: Int
                           )
