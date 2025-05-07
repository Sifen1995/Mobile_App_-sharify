package com.example.sharifytest2.data.model.auth



data class AuthResponseDTO(
    val token: String,
    val success: Boolean,
    val message: String,
    val role: String?,

    )
