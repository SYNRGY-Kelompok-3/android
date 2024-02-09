package com.synrgy.travelid.domain.model.main

data class FlightById(
    val flightTime: String,
    val originCity: String,
    val originAirport: String,
    val airline: String,
    val pathLogo: String,
    val flightNumber: String,
    val passengerClass: String,
    val duration: String,
    val arrivedTime: String,
    val destinationCity: String,
    val destinationAirport: String,
    val luggage: String,
    val freeMeal: String,
    val discountPrice: Int
)
