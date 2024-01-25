package com.synrgy.travelid.data.remote.response.auth

import com.synrgy.travelid.domain.model.auth.ResetPassword
import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse (

    @SerializedName("data"    ) var data    : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

)

fun ResetPasswordResponse.toUpdateUser(): ResetPassword {
    return ResetPassword(
        data = data.orEmpty(),
        message = message.orEmpty(),
        status = status ?: 0
    )
}