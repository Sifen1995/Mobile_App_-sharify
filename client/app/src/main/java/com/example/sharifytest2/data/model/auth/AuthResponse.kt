package com.example.sharifytest2.data.model.auth

data class AuthResponse(val success: Boolean,
                        val message: String,
                        val role: String? = "person")