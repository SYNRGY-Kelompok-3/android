package com.synrgy.travelid.presentation.forgotpassword

import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.domain.repository.AuthRepository

class LupaPasswordUseCase (
    private val authRepository: AuthRepository,
    private val insertTokenUseCase: InsertTokenUseCase,
){
    suspend operator fun invoke(request: ResetPasswordRequest): Boolean{
        val result = authRepository.lupaPassword(request)
//        val token = result.token.orEmpty()
//        insertTokenUseCase.invoke(token = token)
        return true
    }
}