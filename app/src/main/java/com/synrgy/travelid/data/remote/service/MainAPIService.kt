package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.main.EditProfilePictureResponse
import com.synrgy.travelid.data.remote.response.main.EditProfileResponse
import com.synrgy.travelid.data.remote.response.main.NotificationResponse
import com.synrgy.travelid.data.remote.response.main.UserProfileResponse
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface MainAPIService {
    // Profile
    @GET("user/detail-profile")
    suspend fun userProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse

    @PUT("customers/update")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body request: EditProfileRequest
    ): EditProfileResponse

    @Multipart
    @PUT("customers/updateProfilePicture")
    suspend fun editProfilePicture(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part? = null,
        @Part("idCustomer") id: RequestBody?
    ): EditProfilePictureResponse

    // Order History


    // Notification
    @GET("notification/getByCustomerId/{id}")
    suspend fun notification(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): List<NotificationResponse>

    // Article

}