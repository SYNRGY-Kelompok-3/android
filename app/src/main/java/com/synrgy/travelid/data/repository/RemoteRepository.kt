package com.febi.projek.data.repository

import com.febi.projek.data.auth.toUpdateUser
import com.febi.projek.data.local.DataStoreManager
import com.febi.projek.data.remote.service.APIService
import com.febi.projek.domain.model.auth.ResetPassword
import com.febi.projek.domain.model.auth.ResetPasswordRequest
import com.febi.projek.domain.repo.AuthRepository
import com.febi.projek.domain.repo.TokenRepository
import kotlinx.coroutines.flow.firstOrNull

class RemoteRepository (
    private val dataStore: DataStoreManager,
    private val apiService: APIService,
): TokenRepository, AuthRepository {
    override suspend fun lupaPassword(request: ResetPasswordRequest): ResetPassword {
        return apiService.resetPassword(request).toUpdateUser()
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