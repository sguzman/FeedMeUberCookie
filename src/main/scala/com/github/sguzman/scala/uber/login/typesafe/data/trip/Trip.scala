package com.github.sguzman.scala.uber.login.typesafe.data.trip

import java.util.UUID

case class Trip(
               dropOffAddress: Option[String],
               marketplace: String,
               licensePlate: String,
               customRouteMap: Option[String],
               totalToll: Option[String],
               cashCollected: Option[String],
               isProcessing: Boolean,
               timezone: String,
               pickupAddress: Option[String],
               total: String,
               vehicleType: String,
               fareAdjustmentReasons: Array[String],
               currencyCode: String,
               chainUuid: UUID,
               legs: Array[String],
               requestAt: Long,
               breakdown: Array[Breakdown],
               isSurge: Boolean,
               isPoolType: Boolean,
               partnerUuid: UUID,
               routeMap: Option[String],
               uuid: UUID,
               distance: Double,
               fareAdjustmentMessages: Array[String],
               bankDeposit: Option[String],
               status: String,
               disclaimer: Option[String]
               )
