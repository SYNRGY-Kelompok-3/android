package com.synrgy.travelid.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.UserRegister

data class RegisterResponse (

    @SerializedName("data"    ) var data    : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

)

fun RegisterResponse.toUserRegister(): UserRegister {
    return UserRegister(
        data = data.orEmpty(),
        message = message.orEmpty(),
        status = status ?: 0
    )
}