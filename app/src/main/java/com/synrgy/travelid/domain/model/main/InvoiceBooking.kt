package com.synrgy.travelid.domain.model.main

data class InvoiceBooking(
    val flightNumber: String,
    val createdDate: String,
    val bankPembayaran: String,
    val namaRekening: String,
    val nomorRekening: String,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val customerName: String,
    val airline: String,
    val totalPrice: Int,
    val serviceFee: Int
)
