package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.ConfirmOtpRegisterResponse
import com.synrgy.travelid.data.remote.response.RegisterResponse
import com.synrgy.travelid.domain.model.UserRegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("user-register/register")
    suspend fun register(
        @Body request: UserRegisterRequest
    ): RegisterResponse

    @GET("user-register/register-confirm-otp/{otp}")
    suspend fun confirmOtpRegister(
        @Path("otp") otp: String
    ): ConfirmOtpRegisterResponse

}
