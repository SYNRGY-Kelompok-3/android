package com.synrgy.travelid.domain.model.auth

data class UserConfirmOtpRegister (
    val data: String,
    val message: String,
    val status: Int
)