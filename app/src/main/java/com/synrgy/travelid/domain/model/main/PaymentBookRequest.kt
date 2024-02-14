package com.synrgy.travelid.domain.model.main

data class PaymentBookRequest(
    val booking: Booking,
    val bankPembayaran: String,
    val namaRekening: String,
    val nomorRekening: String,
    val masaBerlaku: String,
    val cvvCvn: String
)

data class Booking(
    val id: Int
)
