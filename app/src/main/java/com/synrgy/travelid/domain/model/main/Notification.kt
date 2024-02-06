package com.synrgy.travelid.domain.model.main

data class Notification(
    val id: Int,
    val message: String,
    val timestamp: String,
    var isBadgeRead: Boolean = false
)
