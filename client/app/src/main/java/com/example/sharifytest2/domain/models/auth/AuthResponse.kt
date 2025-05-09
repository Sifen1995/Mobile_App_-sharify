package com.example.sharifytest2.domain.models.auth

data class AuthResponse(
    val id:String?,
    val success: Boolean,
    val message: String,
    val role: String? = "person",
    val token : String? = null
)