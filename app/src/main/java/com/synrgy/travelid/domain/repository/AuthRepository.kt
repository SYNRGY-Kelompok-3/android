package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.ResetPassword
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.model.UpdatePassword
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.ValidateOTP
import com.synrgy.travelid.domain.model.ValidateOTPRequest

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
}