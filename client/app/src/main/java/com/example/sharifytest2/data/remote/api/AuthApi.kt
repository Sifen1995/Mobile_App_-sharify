package com.example.sharifytest2.data.auth.remote


import com.example.sharifytest2.data.model.auth.AuthResponseDTO
import com.example.sharifytest2.data.model.auth.LoginRequestDTO
import com.example.sharifytest2.data.model.auth.RegisterRequestDTO

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDTO): Response<AuthResponseDTO>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequestDTO): Response<AuthResponseDTO>






}
