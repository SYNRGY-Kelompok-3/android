package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.main.UserProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MainAPIService {
    @GET("user/detail-profile")
    suspend fun userProfile(
        @Header("Authorization")token: String
    ): UserProfileResponse
}