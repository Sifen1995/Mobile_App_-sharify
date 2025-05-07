package com.example.sharifytest2.domain.auth.models

data class RegisterRequest(val name: String,
                           val email: String,
                           val password: String)