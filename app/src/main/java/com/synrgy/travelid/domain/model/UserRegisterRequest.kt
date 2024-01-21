package com.synrgy.travelid.domain.model

data class UserRegisterRequest (
    val username: String,
    val password: String,
    val fullname: String,
    val identityNumber: String,
    val dateOfBirth: String,
    val gender: String,
    val phoneNumber: String,
)