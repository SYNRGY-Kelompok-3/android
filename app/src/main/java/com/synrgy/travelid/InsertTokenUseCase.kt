package com.synrgy.travelid

import com.synrgy.travelid.domain.repo.TokenRepository

class InsertTokenUseCase (
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke(token: String){
        tokenRepository.setToken(token)
    }
}