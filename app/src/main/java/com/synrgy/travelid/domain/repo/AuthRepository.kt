package com.febi.projek.domain.repo

import com.febi.projek.domain.model.auth.ResetPassword
import com.febi.projek.domain.model.auth.ResetPasswordRequest

interface AuthRepository {

    suspend fun lupaPassword(
        request: ResetPasswordRequest
    ): ResetPassword

    suspend fun validateInput(
        email: String,
    ):Boolean

}