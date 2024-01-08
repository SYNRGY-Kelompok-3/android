package com.febi.projek.data.remote.service

import com.febi.projek.data.auth.ResetPasswordResponse
import com.febi.projek.domain.model.auth.ResetPasswordRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface APIService {
    @PUT("auth/user/password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ) : ResetPasswordResponse
}