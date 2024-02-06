package com.synrgy.travelid.domain.model.main

data class EditProfile(
    val id: Int,
    val name: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String,
    val profilePicture: String,
    val phoneNumber: String
)
