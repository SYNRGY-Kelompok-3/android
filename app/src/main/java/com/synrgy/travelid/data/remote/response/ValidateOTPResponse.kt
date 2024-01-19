package com.synrgy.travelid.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.ValidateOTP

data class ValidateOTPResponse (

    @SerializedName("data"    ) var data    : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

)

fun ValidateOTPResponse.toValidateOTP(): ValidateOTP {
    return ValidateOTP(
        data = data.orEmpty(),
        message = message.orEmpty(),
        status = status ?: 0
    )
}

