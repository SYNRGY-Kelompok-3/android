package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.EditProfile
import com.synrgy.travelid.domain.model.main.UserProfile

data class EditProfileResponse (

    @SerializedName("data 1"  )  var data1       : Data1?  = Data1(),
    @SerializedName("message" )  var message     : String? = null,
    @SerializedName("data 2"  )  var data2       : Data2?  = Data2(),
    @SerializedName("status"  )  var status      : Int?    = null

) {
    data class Roles (

        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null,
        @SerializedName("type" ) var type : String? = null

    )

    data class Authorities (

        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null,
        @SerializedName("type" ) var type : String? = null

    )

    data class Data1 (

        @SerializedName("id"             ) var id             : Int?                   = null,
        @SerializedName("username"       ) var username       : String?                = null,
        @SerializedName("fullname"       ) var fullname       : String?                = null,
        @SerializedName("otp"            ) var otp            : String?                = null,
        @SerializedName("otpExpiredDate" ) var otpExpiredDate : String?                = null,
        @SerializedName("roles"          ) var roles          : ArrayList<Roles>       = arrayListOf(),
        @SerializedName("authorities"    ) var authorities    : ArrayList<Authorities> = arrayListOf()

    )

    data class Data2 (

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

fun EditProfileResponse.Data2.toEditProfile(): EditProfile {
    return EditProfile(
        id = id ?: 0,
        name = name.orEmpty(),
        email = email.orEmpty(),
        dateOfBirth = dateOfBirth.orEmpty(),
        gender = gender.orEmpty(),
        profilePicture = profilePicture.orEmpty(),
        phoneNumber = phoneNumber.orEmpty(),
    )
}