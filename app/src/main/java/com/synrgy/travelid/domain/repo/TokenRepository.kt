package com.synrgy.travelid.domain.repo

interface TokenRepository {
    suspend fun setToken(token: String)

    suspend fun getToken(): String?

    suspend fun clearToken()
}