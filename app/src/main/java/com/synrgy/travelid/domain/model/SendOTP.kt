package com.synrgy.travelid.domain.model

data class SendOTP(
    val data: String,
    val message: String,
    val status: Int
)
