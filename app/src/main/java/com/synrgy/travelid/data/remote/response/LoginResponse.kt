package com.synrgy.travelid.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.UserLogin
import com.synrgy.travelid.domain.model.UserRegister

data class LoginResponse (

    @SerializedName("access_token"  ) var accessToken  : String? = null,
    @SerializedName("refresh_token" ) var refreshToken : String? = null,
    @SerializedName("scope"         ) var scope        : String? = null,
    @SerializedName("token_type"    ) var tokenType    : String? = null,
    @SerializedName("expires_in"    ) var expiresIn    : Int?    = null,
    @SerializedName("jti"           ) var jti          : String? = null

)

fun LoginResponse.toUserLogin(): UserLogin {
    return UserLogin(
        accessToken = accessToken.orEmpty(),
        refreshToken = refreshToken.orEmpty(),
        scope = scope.orEmpty(),
        tokenType = tokenType.orEmpty(),
        expiresIn = expiresIn ?: 0,
        jti = jti.orEmpty()
    )
}
