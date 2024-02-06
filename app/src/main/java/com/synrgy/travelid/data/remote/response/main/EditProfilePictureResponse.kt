package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.EditProfile
import com.synrgy.travelid.domain.model.main.EditProfilePicture

data class EditProfilePictureResponse (

    @SerializedName("data"    ) var data    : Data?   = Data(),
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

) {
    data class Data (

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

fun EditProfilePictureResponse.Data.toEditProfilePicture(): EditProfilePicture {
    return EditProfilePicture(
        id = id ?: 0,
        name = name.orEmpty(),
        email = email.orEmpty(),
        dateOfBirth = dateOfBirth.orEmpty(),
        gender = gender.orEmpty(),
        profilePicture = profilePicture.orEmpty(),
        phoneNumber = phoneNumber.orEmpty(),
    )
}


