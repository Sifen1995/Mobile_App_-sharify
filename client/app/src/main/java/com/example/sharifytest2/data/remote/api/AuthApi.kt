package com.example.sharifytest2.data.remote.api

import com.example.sharifytest2.data.model.auth.AuthResponse
import com.example.sharifytest2.data.model.auth.LoginRequest
import com.example.sharifytest2.data.model.auth.RegisterRequest
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>



}
