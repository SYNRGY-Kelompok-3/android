package com.synrgy.travelid.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.auth.UserConfirmOtpRegister

data class ConfirmOtpRegisterResponse(
    @SerializedName("data") var data    : String? = null,
    @SerializedName("message") var message : String? = null,
    @SerializedName("status") var status  : Int?    = null
)

fun ConfirmOtpRegisterResponse.toUserConfirmOtpRegister(): UserConfirmOtpRegister {
    return UserConfirmOtpRegister(
        data = data.orEmpty(),
        message = message.orEmpty(),
        status = status?: 0
    )
}