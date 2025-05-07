package com.example.sharifytest2.domain.auth.repository


import com.example.sharifytest2.domain.auth.models.AuthResponse
import com.example.sharifytest2.domain.auth.models.LoginRequest
import com.example.sharifytest2.domain.auth.models.RegisterRequest
import okhttp3.MultipartBody


interface AuthRepository {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
}
