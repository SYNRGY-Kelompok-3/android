package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.auth.ResetPassword
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.model.auth.SendOTP
import com.synrgy.travelid.domain.model.auth.SendOTPRequest
import com.synrgy.travelid.domain.model.auth.UpdatePassword
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.auth.ValidateOTP
import com.synrgy.travelid.domain.model.auth.ValidateOTPRequest
import com.synrgy.travelid.domain.model.auth.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.auth.UserLogin
import com.synrgy.travelid.domain.model.auth.UserLoginRequest
import com.synrgy.travelid.domain.model.auth.UserRegister
import com.synrgy.travelid.domain.model.auth.UserRegisterRequest

interface AuthRepository {
    suspend fun lupaPassword(
        request: ResetPasswordRequest
    ): ResetPassword

    suspend fun validateOTP(
        request: ValidateOTPRequest
    ): ValidateOTP

    suspend fun aturPassword(
        request: UpdatePasswordRequest
    ): UpdatePassword

    suspend fun userRegister(
        request: UserRegisterRequest
    ): UserRegister

    suspend fun userConfirmOtpRegister(
        otp: String
    ): UserConfirmOtpRegister

    suspend fun sendOTPRegister(
        request: SendOTPRequest
    ): SendOTP

    suspend fun userLogin(
        request: UserLoginRequest
    ): UserLogin
}