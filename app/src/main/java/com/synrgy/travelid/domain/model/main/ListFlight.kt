package com.synrgy.travelid.domain.model.main

data class ListFlight(
    val id: Int,
    val airline: String,
    val pathLogo: String,
    val discountPrice: Int,
    val transit: String,
    val flightTime: String,
    val arrivedTime: String,
    val originAirport: String,
    val destinationAirport: String,
    val duration: String,
    val luggage: String,
    val freeMeal: String
)
