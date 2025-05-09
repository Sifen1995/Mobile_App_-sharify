package com.example.sharifytest2.domain.useCase.auth


import com.example.sharifytest2.domain.models.auth.DeleteResponse
import com.example.sharifytest2.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase  @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(userId: String): DeleteResponse {
        return authRepository.deleteAccount(userId) // ✅ Pass user ID
    }
}