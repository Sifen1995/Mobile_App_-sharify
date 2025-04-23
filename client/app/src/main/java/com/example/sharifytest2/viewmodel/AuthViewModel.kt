package com.example.sharifytest2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharifytest2.data.model.auth.LoginRequest
import com.example.sharifytest2.data.model.auth.RegisterRequest

import com.example.sharifytest2.data.remote.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // 🔹 UI State to track registration process
    data class AuthUiState(
        val isLoading: Boolean = false,
        val success: Boolean = false,
        val error: String? = null,
        val userRole: String? = null // "admin" or "user"
    )

    private val _authState = MutableStateFlow(AuthUiState())
    val authState: StateFlow<AuthUiState> = _authState

    // 🔹 Function to handle user registration
    fun registerUser(name: String, email: String, password: String) {
        Log.d("RegisterDebug", "registerUser called with $name, $email,$password")
        viewModelScope.launch {
            _authState.value = AuthUiState(isLoading = true)

            try {
                val response = repository.registerUser(
                    RegisterRequest(name = name, email = email, password = password)
                )

                if (response.success) {
                    _authState.value = AuthUiState(success = true)
                } else {
                    _authState.value = AuthUiState(error = response.message ?: "Unknown error")
                }

            } catch (e: Exception) {
                _authState.value = AuthUiState(error = e.localizedMessage ?: "Something went wrong")
            }
        }
    }

    // 🔹 Reset error so it's not shown repeatedly
    fun clearError() {
        _authState.update { it.copy(error = null) }
    }

    // 🔹 Reset success after navigation or toast
    fun resetSuccess() {
        _authState.update { it.copy(success = false) }
    }

    fun loginUser(email: String, password: String) {
        Log.d("LoginDebug", "loginUser called with $email, $password")
        viewModelScope.launch {
            _authState.value = AuthUiState(isLoading = true)

            try {
                val response = repository.loginUser(

                    LoginRequest(email = email, password = password) // Adjust the model if needed
                )

                if (response.success) {

                    _authState.value = AuthUiState(
                        success = true,
                        userRole = response.role)
                } else {
                    _authState.value = AuthUiState(error = response.message ?: "Unknown error")
                }

            } catch (e: Exception) {
                _authState.value = AuthUiState(error = e.localizedMessage ?: "Something went wrong")
            }
        }
    }




}



