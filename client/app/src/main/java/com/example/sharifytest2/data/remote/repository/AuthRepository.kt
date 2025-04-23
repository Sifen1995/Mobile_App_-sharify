package com.example.sharifytest2.data.remote.repository

import com.example.sharifytest2.data.model.auth.AuthResponse
import com.example.sharifytest2.data.model.auth.LoginRequest
import com.example.sharifytest2.data.model.auth.RegisterRequest
import com.example.sharifytest2.data.remote.RetrofitInstance

class AuthRepository {
    suspend fun registerUser(request: RegisterRequest): AuthResponse {
        val response = RetrofitInstance.api.register(request)
        return response.body() ?: AuthResponse(
            false, "Unknown error",
            role = "person"
        )
    }

    suspend fun loginUser(request: LoginRequest): AuthResponse {
        val response = RetrofitInstance.api.login(request)
        return response.body() ?: AuthResponse(
            false, "Unknown error",
            role = "person"
        )
    }
 

}
