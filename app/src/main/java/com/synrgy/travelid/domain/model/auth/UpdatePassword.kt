package com.synrgy.travelid.domain.model.auth

data class UpdatePassword(
    val data: String,
    val message: String,
    val status: Int
)