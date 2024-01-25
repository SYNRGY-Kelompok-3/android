package com.synrgy.travelid.domain.model.auth

class UpdatePasswordRequest (
    val otp: String?,
    val email: String?,
    val newPassword: String?,
    val confirmNewPassword: String?,
)