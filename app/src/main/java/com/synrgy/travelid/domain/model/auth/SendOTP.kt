package com.synrgy.travelid.domain.model.auth

data class SendOTP(
    val data: String,
    val message: String,
    val status: Int
)
