package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.main.ArticleResponse
import com.synrgy.travelid.data.remote.response.main.BookFlightAwayResponse
import com.synrgy.travelid.data.remote.response.main.EditProfilePictureResponse
import com.synrgy.travelid.data.remote.response.main.EditProfileResponse
import com.synrgy.travelid.data.remote.response.main.FlightByIdResponse
import com.synrgy.travelid.data.remote.response.main.InvoiceBookResponse
import com.synrgy.travelid.data.remote.response.main.ListFlightResponse
import com.synrgy.travelid.data.remote.response.main.NotificationResponse
import com.synrgy.travelid.data.remote.response.main.OrderHistoryByIdResponse
import com.synrgy.travelid.data.remote.response.main.OrderHistoryResponse
import com.synrgy.travelid.data.remote.response.main.PaymentBookResponse
import com.synrgy.travelid.data.remote.response.main.SeatByFlightIdResponse
import com.synrgy.travelid.data.remote.response.main.UserProfileResponse
import com.synrgy.travelid.domain.model.main.BookFlightAwayRequest
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import com.synrgy.travelid.domain.model.main.PaymentBookRequest
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
import retrofit2.http.Query

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
    @GET("booking/bookingsByCustomerId")
    suspend fun orderHistory(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int
    ): OrderHistoryResponse

    @GET("booking/{id}")
    suspend fun orderHistoryById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): OrderHistoryByIdResponse

    // Notification
    @GET("notification/getByCustomerId/{id}")
    suspend fun notification(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): List<NotificationResponse>

    // Search Flight
    @GET("flight/listFlights")
    suspend fun searchFlight(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("originCity") originCity: String,
        @Query("destinationCity") destinationCity: String,
        @Query("startDateStr") startDate: String,
        @Query("endDateStr") endDate: String,
        @Query("passengerClass") pasangerClass: String
    ): ListFlightResponse

    @GET("/flight/{id}")
    suspend fun flightById(
        @Path("id") flightId: Int
    ): FlightByIdResponse

    // Book Flight
    @GET("seat/getByFlight/{id}")
    suspend fun seatByFlightId(
        @Path("id") flightId: Int
    ): SeatByFlightIdResponse

    @POST("booking/saveWithDetails")
    suspend fun bookFlightAway(
        @Header("Authorization") token: String,
        @Body request: BookFlightAwayRequest
    ): BookFlightAwayResponse

    @PUT("booking/processPayment")
    suspend fun paymentBook(
        @Header("Authorization") token: String,
        @Body request: PaymentBookRequest
    ): PaymentBookResponse

    @GET("booking/{id}")
    suspend fun invoiceBooking(
        @Header("Authorization") token: String,
        @Path("id") bookId: Int
    ): InvoiceBookResponse
}