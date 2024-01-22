package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.ResetPassword
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.model.SendOTP
import com.synrgy.travelid.domain.model.SendOTPRequest
import com.synrgy.travelid.domain.model.UpdatePassword
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.ValidateOTP
import com.synrgy.travelid.domain.model.ValidateOTPRequest
import com.synrgy.travelid.domain.model.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.UserLogin
import com.synrgy.travelid.domain.model.UserLoginRequest
import com.synrgy.travelid.domain.model.UserRegister
import com.synrgy.travelid.domain.model.UserRegisterRequest

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