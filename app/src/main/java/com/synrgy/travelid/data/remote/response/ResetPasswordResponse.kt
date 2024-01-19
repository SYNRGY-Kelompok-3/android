package com.synrgy.travelid.data.remote.response

import com.synrgy.travelid.domain.model.ResetPassword
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