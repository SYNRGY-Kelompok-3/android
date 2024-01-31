package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.UserProfile

data class NotificationResponse (

    @SerializedName("id"        ) var id        : Int?      = null,
    @SerializedName("customer"  ) var customer  : Customer? = Customer(),
    @SerializedName("message"   ) var message   : String?   = null,
    @SerializedName("timestamp" ) var timestamp : String?   = null

) {
    data class Customer (

        @SerializedName("created_date"   ) var createdDate    : String? = null,
        @SerializedName("updated_date"   ) var updatedDate    : String? = null,
        @SerializedName("id"             ) var id             : Int?    = null,
        @SerializedName("name"           ) var name           : String? = null,
        @SerializedName("identityNumber" ) var identityNumber : String? = null,
        @SerializedName("email"          ) var email          : String? = null,
        @SerializedName("dateOfBirth"    ) var dateOfBirth    : String? = null,
        @SerializedName("gender"         ) var gender         : String? = null,
        @SerializedName("profilePicture" ) var profilePicture : String? = null,
        @SerializedName("phoneNumber"    ) var phoneNumber    : String? = null

    )
}

fun NotificationResponse.toNotification(): Notification {
    return Notification(
        id = id ?: 0,
        message = message.orEmpty(),
        timestamp = timestamp.orEmpty()
    )
}
