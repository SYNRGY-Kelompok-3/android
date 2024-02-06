package com.synrgy.travelid.data.repository

import com.synrgy.travelid.data.local.DataStoreManager
import com.synrgy.travelid.data.remote.response.auth.toSendOTPPassword
import com.synrgy.travelid.data.remote.response.auth.toUpdatePassword
import com.synrgy.travelid.data.remote.response.auth.toUpdateUser
import com.synrgy.travelid.data.remote.response.auth.toUserConfirmOtpRegister
import com.synrgy.travelid.data.remote.response.auth.toUserLogin
import com.synrgy.travelid.data.remote.response.auth.toUserRegister
import com.synrgy.travelid.data.remote.response.auth.toValidateOTP
import com.synrgy.travelid.data.remote.response.main.toEditProfile
import com.synrgy.travelid.data.remote.response.main.toEditProfilePicture
import com.synrgy.travelid.data.remote.response.main.toFetchArticle
import com.synrgy.travelid.data.remote.response.main.toNotification
import com.synrgy.travelid.data.remote.response.main.toOrderHistory
import com.synrgy.travelid.data.remote.response.main.toOrderHistoryById
import com.synrgy.travelid.data.remote.response.main.toUserProfile
import com.synrgy.travelid.data.remote.service.AuthAPIService
import com.synrgy.travelid.data.remote.service.MainAPIService
import com.synrgy.travelid.data.remote.service.SideAPIService
import com.synrgy.travelid.domain.model.auth.ResetPassword
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.model.auth.SendOTP
import com.synrgy.travelid.domain.model.auth.SendOTPRequest
import com.synrgy.travelid.domain.model.auth.UpdatePassword
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.auth.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.auth.UserLogin
import com.synrgy.travelid.domain.model.auth.UserLoginRequest
import com.synrgy.travelid.domain.model.auth.UserRegister
import com.synrgy.travelid.domain.model.auth.UserRegisterRequest
import com.synrgy.travelid.domain.model.auth.ValidateOTP
import com.synrgy.travelid.domain.model.auth.ValidateOTPRequest
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.model.main.EditProfile
import com.synrgy.travelid.domain.model.main.EditProfilePicture
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.domain.repository.AuthRepository
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.SideRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val dataStore: DataStoreManager,
    private val authAPIService: AuthAPIService,
    private val mainAPIService: MainAPIService,
    private val sideAPIService: SideAPIService
): TokenRepository, AuthRepository, MainRepository, SideRepository {
    override suspend fun lupaPassword(request: ResetPasswordRequest): ResetPassword {
        return authAPIService.resetPassword(request).toUpdateUser()
    }

    override suspend fun validateOTP(request: ValidateOTPRequest): ValidateOTP {
        return authAPIService.validateOTP(request).toValidateOTP()
    }

    override suspend fun aturPassword(request: UpdatePasswordRequest): UpdatePassword {
        return authAPIService.updatePassword(request).toUpdatePassword()
    }

    override suspend fun setToken(token: String) {
        dataStore.setToken(token)
    }

    override suspend fun getToken(): String? {
        return dataStore.getToken().firstOrNull()
    }

    override suspend fun clearToken() {
        setToken("")
    }

    override suspend fun userRegister(request: UserRegisterRequest): UserRegister {
        return authAPIService.register(request).toUserRegister()
    }

    override suspend fun userConfirmOtpRegister(otp: String): UserConfirmOtpRegister {
        return authAPIService.confirmOtpRegister(otp).toUserConfirmOtpRegister()
    }

    override suspend fun sendOTPRegister(request: SendOTPRequest): SendOTP {
        return authAPIService.sendOTPRegister(request).toSendOTPPassword()
    }

    override suspend fun userLogin(request: UserLoginRequest): UserLogin {
        return authAPIService.login(request).toUserLogin()
    }

    override suspend fun userProfile(token: String): UserProfile {
        return mainAPIService.userProfile(token = "Bearer $token").data2!!.toUserProfile()
    }

    override suspend fun editProfile(token: String, request: EditProfileRequest): EditProfile {
        return mainAPIService.editProfile(token = "Bearer $token", request).data2!!.toEditProfile()
    }

    override suspend fun editProfilePicture(
        token: String,
        profilePicture: MultipartBody.Part?,
        idCustomer: RequestBody?
    ): EditProfilePicture {
        return mainAPIService.editProfilePicture(
            token = "Bearer $token", profilePicture, idCustomer
        ).data!!.toEditProfilePicture()
    }

    override suspend fun notification(token: String, id: Int): List<Notification> {
        return mainAPIService.notification(
            token = "Bearer $token", id
        ).map { response -> response.toNotification() }
    }

    override suspend fun orderHistory(token: String, customerId: Int): List<OrderHistory> {
        return mainAPIService.orderHistory(
            token = "Bearer $token", customerId
        ).data.map { response -> response.toOrderHistory() }
    }

    override suspend fun orderHistoryById(token: String, id: Int): OrderHistoryById {
        return mainAPIService.orderHistoryById(
            token = "Bearer $token", id
        ).data!!.toOrderHistoryById()
    }


    override suspend fun fetchArticle(): List<Article> {
        return sideAPIService.fetchArticle().map { response -> response.toFetchArticle() }
    }
}