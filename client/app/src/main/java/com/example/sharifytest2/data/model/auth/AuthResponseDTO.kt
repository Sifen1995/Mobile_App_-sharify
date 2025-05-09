package com.example.sharifytest2.data.model.auth



data class AuthResponseDTO(
    val id:String?,
    val success: Boolean,
    val message: String,
    val role: String? = "person",
    val token : String? = null

)