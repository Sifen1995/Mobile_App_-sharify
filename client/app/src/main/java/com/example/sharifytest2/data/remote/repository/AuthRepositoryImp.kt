package com.example.sharifytest2.data.auth.repository


import android.util.Log
import com.example.sharifytest2.data.auth.remote.AuthApi
import com.example.sharifytest2.data.local.UserPreferences
import com.example.sharifytest2.data.model.auth.LoginRequestDTO
import com.example.sharifytest2.data.model.auth.RegisterRequestDTO
import com.example.sharifytest2.data.model.auth.toDomainModel

import com.example.sharifytest2.domain.models.auth.AuthResponse
import com.example.sharifytest2.domain.models.auth.DeleteResponse
import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.models.auth.LogoutResponse
import com.example.sharifytest2.domain.models.auth.RegisterRequest
import com.example.sharifytest2.domain.models.auth.UpdateProfileResponse
import com.example.sharifytest2.domain.repository.AuthRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val userPreferences: UserPreferences // ✅ Inject UserPreferences
) : AuthRepository {

    override suspend fun login(request: LoginRequest): AuthResponse {
        val response = api.login(LoginRequestDTO(request.email, request.password))

        return if (response.isSuccessful) {
            response.body()?.let {
                if (it.success && !it.token.isNullOrEmpty()) {
                    userPreferences.saveAuthToken(it.token)
                    userPreferences.saveUserRole(it.role ?: "user")
                    userPreferences.saveUserId(it.id ?: "") // ✅ Store userId

                    println("🔐 Token, Role & UserId saved successfully: ${it.token}, ${it.role}, ${it.id}")
                } else {
                    println("❌ Missing token in login response!")
                }
                AuthResponse(it.id, it.success, it.message, it.role, it.token)
            } ?: AuthResponse(null, false, "", "Unknown Error", null)
        } else {
            AuthResponse(null, false, "", response.errorBody()?.string() ?: "Login failed", null)
        }
    }


    override suspend fun register(request: RegisterRequest): AuthResponse {
        val response = api.register(RegisterRequestDTO(request.name, request.email, request.password))

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            println("Register Error: $errorBody")
            return AuthResponse(null, false, "", errorBody ?: "Unknown Error", null) // ✅ Fix: Ensure userId is handled
        }

        return response.body()?.let {
            AuthResponse(it.id,it.success, it.message, it.role,it.token,) // ✅ Fix: Include userId in response
        } ?: AuthResponse(null, false, "", "Unknown Error", null)
    }



    override suspend fun updateProfile(
        userId: String,
        name: RequestBody,
        image: MultipartBody.Part?
    ): UpdateProfileResponse {
//        val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())

        // Log Request Details
        Log.d("Profile Update", "Sending request to update profile")
        Log.d("Profile Update", "User ID: $userId")
        Log.d("Profile Update", "Name: $name")
        Log.d("Profile Update", "Image: ${image?.headers}")

        val response = api.updateProfile(userId, name, image)

        if (response.isSuccessful) {
            val body = response.body()
            Log.d("Profile Update", "Response Success: ${body?.success}")
            Log.d("Profile Update", "Message: ${body?.message}")
            return UpdateProfileResponse(
                success = body?.success ?: false,
                message = body?.message ?: "No message received",
                updatedUser = body?.updatedUser?.toDomain()
            )
        } else {
            val error = response.errorBody()?.string()
            Log.e("Profile Update", "Response Failed: ${response.code()} - $error")
            return UpdateProfileResponse(false, error ?: "Unknown error", null)
        }
    }

    override suspend fun logout(): LogoutResponse {
        return try {
            val response = api.logout()
            if (response.isSuccessful) {
                response.body()?.toDomainModel() ?: throw Exception("Unexpected response format")
            } else {
                throw Exception("Logout failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Logout error: ${e.message}")
        }
    }
    override suspend fun deleteAccount(userId: String): DeleteResponse {
        return try {
            val response = api.deleteAccount(userId)
            if (response.isSuccessful) {
                response.body()?.toDomainModel() ?: throw Exception("Unexpected response format")
            } else {
                throw Exception("Account deletion failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Delete error: ${e.message}")
        }
    }









}
