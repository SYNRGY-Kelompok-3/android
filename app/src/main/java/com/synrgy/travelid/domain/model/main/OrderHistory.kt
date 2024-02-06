package com.synrgy.travelid.domain.model.main

data class OrderHistory(
    val id: Int,
    val totalPrice: Int,
    val paid: String,
    val flightNumber: String,
    val originCity: String,
    val destinationCity: String,
    val flightTime: String
)
