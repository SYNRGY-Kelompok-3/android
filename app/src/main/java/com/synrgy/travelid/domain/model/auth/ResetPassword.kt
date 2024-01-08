package com.synrgy.travelid.domain.model.auth

data class ResetPassword(
    val data: String,
    val message: String,
    val status: Int
)
