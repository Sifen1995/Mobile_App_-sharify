package com.example.sharifytest2.domain.useCase.auth



import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest) = repository.login(request)
}
