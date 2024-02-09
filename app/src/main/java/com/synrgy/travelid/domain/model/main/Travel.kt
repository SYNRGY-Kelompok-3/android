package com.synrgy.travelid.domain.model.main

data class Travel(
    val airlineName: String,
    val price: String,
    val departureDate: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val departureTime: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val flightDuration: String
)
