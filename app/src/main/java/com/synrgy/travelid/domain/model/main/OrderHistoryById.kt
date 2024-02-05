package com.synrgy.travelid.domain.model.main

data class OrderHistoryById(
    val totalPrice: Int,
    val paid: String,
    val flightNumber: String,
    val originCity: String,
    val destinationCity: String,
    val flightTime: String,
    val arrivedTime: String,
    val originAirport: String,
    val destinationAirport: String,
    val name: String,
    val airline: String,
    val pathLogo: String,
    val passengerClass: String,
    val duration: String,
    val luggage: String,
    val freeMeal: String
)
