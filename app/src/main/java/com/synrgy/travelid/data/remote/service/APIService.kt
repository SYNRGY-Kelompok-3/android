package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.ConfirmOtpRegisterResponse
import com.synrgy.travelid.data.remote.response.RegisterResponse
import com.synrgy.travelid.data.remote.response.ResetPasswordResponse
import com.synrgy.travelid.data.remote.response.SendOTPRegisterResponse
import com.synrgy.travelid.data.remote.response.UpdatePasswordResponse
import com.synrgy.travelid.data.remote.response.ValidateOTPResponse
import com.synrgy.travelid.domain.model.ValidateOTPRequest
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.model.SendOTPRequest
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.UserRegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    // Register
    @POST("user-register/register")
    suspend fun register(
        @Body request: UserRegisterRequest
    ): RegisterResponse

    @GET("user-register/register-confirm-otp/{otp}")
    suspend fun confirmOtpRegister(
        @Path("otp") otp: String
    ): ConfirmOtpRegisterResponse

    @POST("user-register/send-otp")
    suspend fun sendOTPRegister(
        @Body request: SendOTPRequest
    ) : SendOTPRegisterResponse

    // Forgot Password
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