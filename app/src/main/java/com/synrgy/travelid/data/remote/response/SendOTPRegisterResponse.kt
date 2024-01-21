package com.synrgy.travelid.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.SendOTP
import com.synrgy.travelid.domain.model.UpdatePassword

data class SendOTPRegisterResponse (
    @SerializedName("data"    ) var data    : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null
)

fun SendOTPRegisterResponse.toSendOTPPassword(): SendOTP {
    return SendOTP(
        data = data.orEmpty(),
        message = message.orEmpty(),
        status = status ?: 0
    )
}