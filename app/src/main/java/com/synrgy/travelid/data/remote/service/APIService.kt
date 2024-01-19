package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.ResetPasswordResponse
import com.synrgy.travelid.data.remote.response.UpdatePasswordResponse
import com.synrgy.travelid.data.remote.response.ValidateOTPResponse
import com.synrgy.travelid.domain.model.ValidateOTPRequest
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("forget-password/send")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ) : ResetPasswordResponse

    @POST("forget-password/validate")
    suspend fun validateOTP(
        @Body request : ValidateOTPRequest
    ): ValidateOTPResponse

    @POST("forget-password/change-password")
    suspend fun updatePassword(
        @Body request: UpdatePasswordRequest
    ) : UpdatePasswordResponse
}