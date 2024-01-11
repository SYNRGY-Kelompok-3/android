package com.synrgy.travelid.data.repository

import com.synrgy.travelid.data.remote.response.toUserConfirmOtpRegister
import com.synrgy.travelid.data.remote.response.toUserRegister
import com.synrgy.travelid.data.remote.service.AuthAPI
import com.synrgy.travelid.domain.model.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.UserRegister
import com.synrgy.travelid.domain.model.UserRegisterRequest
import com.synrgy.travelid.domain.repository.AuthRepository
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val authAPI: AuthAPI
): AuthRepository{
    override suspend fun userRegister(request: UserRegisterRequest): UserRegister {
        return authAPI.register(request).toUserRegister()
    }

    override suspend fun userConfirmOtpRegister(otp: String): UserConfirmOtpRegister {
        return authAPI.confirmOtpRegister(otp).toUserConfirmOtpRegister()
    }
}