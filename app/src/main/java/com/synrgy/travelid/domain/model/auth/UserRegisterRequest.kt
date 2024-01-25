package com.synrgy.travelid.domain.model.auth

data class UserRegisterRequest (
    val username: String,
    val password: String,
    val fullname: String,
    val phoneNumber: String
)