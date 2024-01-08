package com.febi.projek.domain.repo

interface TokenRepository {
    suspend fun setToken(token: String)

    suspend fun getToken(): String?

    suspend fun clearToken()
}