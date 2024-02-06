package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.model.main.EditProfile
import com.synrgy.travelid.domain.model.main.EditProfilePicture
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.domain.model.main.UserProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MainRepository {
    suspend fun userProfile(
        token: String
    ): UserProfile

    suspend fun editProfile(
        token: String, request: EditProfileRequest
    ): EditProfile

    suspend fun editProfilePicture(
        token: String, profilePicture: MultipartBody.Part?, idCustomer: RequestBody?
    ): EditProfilePicture

    suspend fun notification(
        token: String, id: Int
    ): List<Notification>

    suspend fun orderHistory(
        token: String, customerId: Int
    ): List<OrderHistory>

    suspend fun orderHistoryById(
        token: String, id: Int
    ): OrderHistoryById
}