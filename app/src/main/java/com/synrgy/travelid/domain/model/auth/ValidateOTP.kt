package com.synrgy.travelid.domain.model.auth

data class ValidateOTP(
    val data: String,
    val message: String,
    val status: Int
)
