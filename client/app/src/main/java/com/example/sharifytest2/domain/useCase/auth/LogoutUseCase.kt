package com.example.sharifytest2.domain.useCase.auth

import com.example.sharifytest2.domain.models.auth.LogoutResponse
import com.example.sharifytest2.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor (private val authRepository: AuthRepository)

{
    suspend operator fun invoke(): LogoutResponse {
        return authRepository.logout()
    }
}