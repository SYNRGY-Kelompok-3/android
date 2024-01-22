package com.synrgy.travelid.domain.model

data class UserRegisterRequest (
    val username: String,
    val password: String,
    val fullname: String,
    val phoneNumber: String
)