package com.synrgy.travelid.domain.model.main

data class EditProfileRequest(
    val id: Int,
    val name: String,
    val gender: String,
    val dateOfBirth: String,
    val phoneNumber: String
)
