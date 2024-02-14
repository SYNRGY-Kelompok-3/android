package com.synrgy.travelid.domain.model.main

data class BookFlightAwayRequest(
    val customer: Customer,
    val flight: Flight,
    val email: String,
    val phoneNumber: String,
    val listBookingDetail: List<BookingDetail>
)

data class Customer(
    val id: Int
)

data class Flight(
    val id: Int
)

data class BookingDetail(
    val customerName: String,
    val phoneNumber: String?,
    var seatNumber: String?,
    var totalSeatPrice: Int?,
    val category: String
) {
    constructor(customerName: String, category: String) : this(
        customerName = customerName,
        phoneNumber = null,
        seatNumber = null,
        totalSeatPrice = null,
        category = category
    )
}

