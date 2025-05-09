package com.example.sharifytest2.domain.repository

import com.example.sharifytest2.domain.models.auth.AuthResponse
import com.example.sharifytest2.domain.models.auth.DeleteResponse
import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.models.auth.LogoutResponse
import com.example.sharifytest2.domain.models.auth.RegisterRequest
import com.example.sharifytest2.domain.models.auth.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface AuthRepository {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
    suspend fun updateProfile(
        userId: String,
        name: RequestBody,
        image: MultipartBody.Part?
    ): UpdateProfileResponse
    suspend fun logout(): LogoutResponse



    suspend fun deleteAccount(userId: String): DeleteResponse


}
