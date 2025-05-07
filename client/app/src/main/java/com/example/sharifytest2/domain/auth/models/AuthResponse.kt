package com.example.sharifytest2.domain.auth.models

data class AuthResponse(val success: Boolean,
                        val message: String,
                        val role: String? = "person",
                        val token :String? = null,)