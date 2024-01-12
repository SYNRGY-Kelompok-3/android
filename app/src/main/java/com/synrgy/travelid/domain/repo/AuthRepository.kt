package com.synrgy.travelid.domain.repo

import com.synrgy.travelid.domain.model.auth.ResetPassword
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.model.auth.UpdatePassword
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest

interface AuthRepository {

    suspend fun lupaPassword(
        request: ResetPasswordRequest
    ): ResetPassword

    suspend fun validateInput(
        email: String,
    ):Boolean

    suspend fun aturPassword(
        request: UpdatePasswordRequest
    ): UpdatePassword
}