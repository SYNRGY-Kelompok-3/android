package com.synrgy.travelid.domain.model.auth

data class UserLoginRequest(
    val username: String,
    val password: String
)
