package com.example.sharifytest2.domain.auth.repository


import com.example.sharifytest2.domain.models.auth.AuthResponse
import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.models.auth.RegisterRequest


interface AuthRepository {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
}
