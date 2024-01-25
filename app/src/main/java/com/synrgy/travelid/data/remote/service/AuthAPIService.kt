package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.auth.ConfirmOtpRegisterResponse
import com.synrgy.travelid.data.remote.response.auth.LoginResponse
import com.synrgy.travelid.data.remote.response.auth.RegisterResponse
import com.synrgy.travelid.data.remote.response.auth.ResetPasswordResponse
import com.synrgy.travelid.data.remote.response.auth.SendOTPRegisterResponse
import com.synrgy.travelid.data.remote.response.auth.UpdatePasswordResponse
import com.synrgy.travelid.data.remote.response.auth.ValidateOTPResponse
import com.synrgy.travelid.domain.model.auth.ValidateOTPRequest
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.model.auth.SendOTPRequest
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.auth.UserLoginRequest
import com.synrgy.travelid.domain.model.auth.UserRegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthAPIService {
    // Login
    @POST("user-login/login")
    suspend fun login(
        @Body request: UserLoginRequest
    ): LoginResponse

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