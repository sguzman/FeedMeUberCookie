package com.github.sguzman.scala.uber.data.typesafe.data.statement

import java.util.UUID

case class Partner(
                  status: Int,
                  contact_number: String,
                  last_name: String,
                  uuid: UUID,
                  city_id: Int,
                  picture_url: String,
                  country_id: Int,
                  first_name: String,
                  email: String,
                  token: String,
                  base_number: Option[String],
                  flow_type: Int,
                  language_code: String,
                  referral_code: String,
                  partner_user_uuid: UUID,
                  id: Option[String]
                  )
