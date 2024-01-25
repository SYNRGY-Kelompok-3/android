package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.main.UserProfile

interface MainRepository {
    suspend fun userProfile(
        token: String
    ): UserProfile
}