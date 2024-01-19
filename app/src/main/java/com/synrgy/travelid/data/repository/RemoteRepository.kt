package com.synrgy.travelid.data.repository

import com.synrgy.travelid.data.remote.response.toUpdatePassword
import com.synrgy.travelid.data.local.DataStoreManager
import com.synrgy.travelid.data.remote.service.APIService
import com.synrgy.travelid.domain.model.ResetPassword
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.repository.AuthRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import com.synrgy.travelid.data.remote.response.toUpdateUser
import com.synrgy.travelid.data.remote.response.toValidateOTP
import com.synrgy.travelid.domain.model.UpdatePassword
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.ValidateOTP
import com.synrgy.travelid.domain.model.ValidateOTPRequest
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val dataStore: DataStoreManager,
    private val apiService: APIService,
): TokenRepository, AuthRepository {
    override suspend fun lupaPassword(request: ResetPasswordRequest): ResetPassword {
        return apiService.resetPassword(request).toUpdateUser()
    }

    override suspend fun validateOTP(request: ValidateOTPRequest): ValidateOTP {
        return apiService.validateOTP(request).toValidateOTP()
    }

    override suspend fun aturPassword(request: UpdatePasswordRequest): UpdatePassword {
        return apiService.updatePassword(request).toUpdatePassword()
    }

    override suspend fun setToken(token: String) {
        dataStore.setToken(token)
    }

    override suspend fun getToken(): String? {
        return dataStore.getToken().firstOrNull()
    }

    override suspend fun clearToken() {
        setToken("")
    }

    override suspend fun userRegister(request: UserRegisterRequest): UserRegister {
        return authApi.register(request).toUserRegister()
    }

    override suspend fun userConfirmOtpRegister(otp: String): UserConfirmOtpRegister {
        return authApi.confirmOtpRegister(otp).toUserConfirmOtpRegister()
    }
}