package com.synrgy.travelid.domain.model.main

import java.io.File

data class EditProfilePictureRequest(
    val profilePicture: File?,
    val idCustomer: Int
)
