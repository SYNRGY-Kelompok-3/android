package com.synrgy.travelid.data.repository

import com.synrgy.travelid.data.auth.toUpdatePassword
import com.synrgy.travelid.data.local.DataStoreManager
import com.synrgy.travelid.data.remote.service.APIService
import com.synrgy.travelid.domain.model.auth.ResetPassword
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.repo.AuthRepository
import com.synrgy.travelid.domain.repo.TokenRepository
import com.synrgy.travelid.data.auth.toUpdateUser
import com.synrgy.travelid.domain.model.auth.UpdatePassword
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import kotlinx.coroutines.flow.firstOrNull

class RemoteRepository (
    private val dataStore: DataStoreManager,
    private val apiService: APIService,
): TokenRepository, AuthRepository {
    override suspend fun lupaPassword(request: ResetPasswordRequest): ResetPassword {
        return apiService.resetPassword(request).toUpdateUser()
    }
    override suspend fun aturPassword(request: UpdatePasswordRequest): UpdatePassword{
        return apiService.updatePassword(request).toUpdatePassword()
    }

    override suspend fun validateInput(
        email: String,
    ): Boolean{
        return email.isNotEmpty()&&
                email.isNotBlank()
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
}