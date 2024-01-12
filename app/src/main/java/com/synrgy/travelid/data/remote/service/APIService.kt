package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.data.auth.ResetPasswordResponse
import com.synrgy.travelid.data.auth.UpdatePasswordResponse
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("forget-password/send")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ) : ResetPasswordResponse

    @POST("forget-password/change-password")
    suspend fun updatePassword(
        @Body request: UpdatePasswordRequest
    ) : UpdatePasswordResponse
}