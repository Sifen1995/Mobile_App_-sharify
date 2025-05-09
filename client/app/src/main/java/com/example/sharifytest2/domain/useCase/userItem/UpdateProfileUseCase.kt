package com.example.sharifytest2.domain.useCase.userItem

import com.example.sharifytest2.domain.models.auth.UpdateProfileResponse
import com.example.sharifytest2.domain.repository.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        userId: String,
        name:RequestBody,
        image: MultipartBody.Part?
    ): UpdateProfileResponse {
        return repository.updateProfile(userId, name, image)
    }
}
