package com.synrgy.travelid.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.ErrorMessage
import com.synrgy.travelid.domain.model.UserRegister

data class ErrorResponse (

    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

)

fun ErrorResponse.toUserRegister(): ErrorMessage {
    return ErrorMessage(
        message = message.orEmpty()
    )
}
