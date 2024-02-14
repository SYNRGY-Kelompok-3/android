package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.model.main.BookFlightAway
import com.synrgy.travelid.domain.model.main.BookFlightAwayRequest
import com.synrgy.travelid.domain.model.main.EditProfile
import com.synrgy.travelid.domain.model.main.EditProfilePicture
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import com.synrgy.travelid.domain.model.main.FlightById
import com.synrgy.travelid.domain.model.main.ListFlight
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.domain.model.main.PaymentBook
import com.synrgy.travelid.domain.model.main.PaymentBookRequest
import com.synrgy.travelid.domain.model.main.SeatByFlightId
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

    suspend fun searchFlight(
        page: Int,
        size: Int,
        originCity: String,
        destinationCity: String,
        startDate: String,
        endDate: String,
        pasangerClass: String
    ): List<ListFlight>

    suspend fun flightById(
        id: Int
    ): FlightById

    suspend fun seatByFlightId(
        flightId: Int
    ): List<SeatByFlightId>

    suspend fun bookFlightAway(
        token: String, request: BookFlightAwayRequest
    ): BookFlightAway

    suspend fun paymentBook(
        token: String, request: PaymentBookRequest
    ): PaymentBook
}