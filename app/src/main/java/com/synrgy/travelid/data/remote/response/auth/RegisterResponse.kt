package com.synrgy.travelid.data.remote.response.auth

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.auth.UserRegister

data class RegisterResponse(
    @SerializedName("data") var data    : String? = null,
    @SerializedName("message") var message : String? = null,
    @SerializedName("status") var status  : Int?    = null
) {
    data class Data (

        @SerializedName("password"    ) var password    : String? = null,
        @SerializedName("PhoneNumber" ) var PhoneNumber : String? = null,
        @SerializedName("fullname"    ) var fullname    : String? = null,
        @SerializedName("username"    ) var username    : String? = null

    )
}

fun RegisterResponse.toUserRegister(): UserRegister {
    return UserRegister(
        data = data.orEmpty(),
        message = message.orEmpty(),
        status = status?: 0
    )
}