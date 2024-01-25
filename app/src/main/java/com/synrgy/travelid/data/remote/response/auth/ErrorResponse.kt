package com.synrgy.travelid.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.auth.ErrorMessage

data class ErrorResponse (

    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

)

fun ErrorResponse.toUserRegister(): ErrorMessage {
    return ErrorMessage(
        message = message.orEmpty()
    )
}
