package com.example.sharifytest2.domain.auth.useCase





import com.example.sharifytest2.domain.models.auth.RegisterRequest
import com.example.sharifytest2.domain.auth.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: RegisterRequest) = repository.register(request)
}
