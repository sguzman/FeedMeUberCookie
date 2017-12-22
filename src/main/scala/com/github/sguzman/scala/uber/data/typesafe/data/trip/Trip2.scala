package com.github.sguzman.scala.uber.data.typesafe.data.trip

case class Trip2(
                  latitude: Option[Double],
                  longitude: Option[Double],
                  trip: Trip
                )