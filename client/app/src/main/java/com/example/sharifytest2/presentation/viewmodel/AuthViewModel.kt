package com.example.sharifytest2.presentation.viewmodel

import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.models.auth.RegisterRequest
import com.example.sharifytest2.presentation.uiStates.AuthUiState


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharifytest2.data.local.UserPreferences

import com.example.sharifytest2.domain.useCase.LoginUseCase
import com.example.sharifytest2.domain.useCase.RegisterUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,

    private val userPreferences: UserPreferences // ✅ Inject UserPreferences
) : ViewModel()
{

    private val _authState = MutableStateFlow(AuthUiState())

    val authState: StateFlow<AuthUiState> = _authState





    init {
        viewModelScope.launch {
            val savedRole = userPreferences.getUserRole()
            _authState.update { it.copy(userRole = savedRole) }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }

            try {
                val result = loginUseCase(LoginRequest(email, password))

                if (result.success) {
                    userPreferences.saveAuthToken(result.token ?: "")
                    userPreferences.saveUserRole(result.role ?: "user") // ✅ Store role safely

                    _authState.update {
                        println("✅ Successfully logged in with role: ${result.role}") // ✅ Debug output
                        it.copy(success = true, userRole = result.role)
                    }
                } else {
                    _authState.update { it.copy(error = result.message) }
                }

            } catch (e: Exception) {
                _authState.update { it.copy(error = e.localizedMessage) }
            }
        }
    }

    fun loadUserRole() {
        viewModelScope.launch {
            val savedRole = userPreferences.getUserRole()
            println("🔍 Retrieved user role from storage: $savedRole") // ✅ Debugging
            _authState.update { it.copy(userRole = savedRole) } // ✅ Make sure UI updates
        }
    }


    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState(isLoading = true)
            try {
                val result = registerUseCase(RegisterRequest(name, email, password))
                _authState.value = AuthUiState(
                    success = result.success,
                    error = result.message,
                    userRole = result.role
                )
            } catch (e: Exception) {
                _authState.value = AuthUiState(error = e.localizedMessage)
            }
        }
    }
    fun clearError() {
        _authState.update { it.copy(error = null) }
    }

    fun resetSuccess() {
        _authState.update { it.copy(success = false) }
    }

}









