package com.example.sharifytest2.domain.models.auth

data class RegisterRequest(val name: String,
                           val email: String,
                           val password: String)