package com.example.sharifytest2.domain.auth.useCase



import com.example.sharifytest2.domain.auth.models.LoginRequest
import com.example.sharifytest2.domain.auth.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest) = repository.login(request)
}
