package com.example.sharifytest2.domain.useCase.auth


import com.example.sharifytest2.domain.models.auth.RegisterRequest
import com.example.sharifytest2.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: RegisterRequest) = repository.register(request)
}
