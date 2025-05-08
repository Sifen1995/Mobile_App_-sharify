package com.example.sharifytest2.data.model.auth



data class AuthResponseDTO(
    val userId:String?,
    val success: Boolean,
    val message: String,
    val role: String? = "person",
    val token : String? = null

)