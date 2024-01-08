package com.febi.projek

import com.febi.projek.domain.repo.TokenRepository

class InsertTokenUseCase (
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke(token: String){
        tokenRepository.setToken(token)
    }
}