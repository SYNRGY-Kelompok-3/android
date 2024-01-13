package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.UserRegister
import com.synrgy.travelid.domain.model.UserRegisterRequest

interface AuthRepository {
    suspend fun userRegister(request: UserRegisterRequest): UserRegister
    suspend fun userConfirmOtpRegister(otp: String): UserConfirmOtpRegister
}