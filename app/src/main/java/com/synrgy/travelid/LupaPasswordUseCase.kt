package com.febi.projek

import com.febi.projek.domain.model.auth.ResetPasswordRequest
import com.febi.projek.domain.repo.AuthRepository

class LupaPasswordUseCase (
    private val authRepository: AuthRepository,
    private val insertTokenUseCase: InsertTokenUseCase,
){
    suspend operator fun invoke(request: ResetPasswordRequest): Boolean{
        val result = authRepository.lupaPassword(request)
        val token = result.token.orEmpty()
        insertTokenUseCase.invoke(token = token)
        return true
    }
}