package com.synrgy.travelid.presentation.auth.forgotpassword

import com.synrgy.travelid.domain.repository.TokenRepository

class InsertTokenUseCase (
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke(token: String){
        tokenRepository.setToken(token)
    }
}