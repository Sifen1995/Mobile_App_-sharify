package com.example.sharifytest2.data.auth.repository


import com.example.sharifytest2.data.auth.remote.AuthApi
import com.example.sharifytest2.data.local.UserPreferences
import com.example.sharifytest2.data.model.auth.LoginRequestDTO
import com.example.sharifytest2.data.model.auth.RegisterRequestDTO

import com.example.sharifytest2.domain.models.auth.AuthResponse
import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.models.auth.RegisterRequest

import com.example.sharifytest2.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val userPreferences: UserPreferences // ✅ Inject UserPreferences
) : AuthRepository {

    override suspend fun login(request: LoginRequest): AuthResponse {
        val response = api.login(LoginRequestDTO(request.email, request.password))

        return response.body()?.let {
            if (it.success && !it.token.isNullOrEmpty()) {
                userPreferences.saveAuthToken(it.token)
                userPreferences.saveUserRole(it.role ?: "user") // ✅ Save role along with token
                println("🔐 Token & role saved successfully: ${it.token}, ${it.role}")
            } else {
                println("❌ Token missing in login response!")
            }
            AuthResponse(it.success, it.message, it.role, it.token)
        } ?: AuthResponse(false, "Unknown Error", null, null)
    }


    override suspend fun register(request: RegisterRequest): AuthResponse {
        val response = api.register(
            RegisterRequestDTO(request.name, request.email, request.password)


        )

        if (!response.isSuccessful) {

            val errorBody = response.errorBody()?.string()
            println("Register Error: $errorBody")
            return AuthResponse(false, errorBody ?: "Unknown Error", null)
        }
        return response.body()?.let {
            AuthResponse(it.success, it.message, it.role)
        } ?: AuthResponse(false, "Unknown Error", null)
    }




}
