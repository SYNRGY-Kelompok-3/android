package com.synrgy.travelid.domain.model

data class UserLogin(
    val accessToken: String,
    val refreshToken : String,
    val scope: String,
    val tokenType: String,
    val expiresIn: Int,
    val jti: String,
)
