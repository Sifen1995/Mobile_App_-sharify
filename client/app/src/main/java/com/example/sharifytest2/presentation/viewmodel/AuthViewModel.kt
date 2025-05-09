package com.example.sharifytest2.presentation.viewmodel

import android.util.Log
import com.example.sharifytest2.domain.models.auth.LoginRequest
import com.example.sharifytest2.domain.models.auth.RegisterRequest
import com.example.sharifytest2.presentation.uiStates.AuthUiState


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharifytest2.data.local.UserPreferences
import com.example.sharifytest2.domain.models.auth.DeleteResponse
import com.example.sharifytest2.domain.models.auth.LogoutResponse
import com.example.sharifytest2.domain.models.auth.User
import com.example.sharifytest2.domain.useCase.auth.DeleteAccountUseCase

import com.example.sharifytest2.domain.useCase.auth.LoginUseCase
import com.example.sharifytest2.domain.useCase.auth.LogoutUseCase
import com.example.sharifytest2.domain.useCase.auth.RegisterUseCase
import com.example.sharifytest2.domain.useCase.userItem.UpdateProfileUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,

    private val userPreferences: UserPreferences, // ✅ Inject UserPreferences
    private val logoutUseCase : LogoutUseCase,

    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

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


    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message
    fun updateProfile(userId: String, name: String, image: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                Log.d(
                    "Profile Update",
                    "Sending request - userId: $userId, name: $name, image: ${image?.headers}"
                )

                val result = updateProfileUseCase(userId, name, image)

                Log.d(
                    "Profile Update",
                    "Response: success=${result.success}, message=${result.message}"
                )

                _message.value = result.message
                if (result.success && result.updatedUser != null) {
                    Log.d("Profile Update", "Updated user: ${result.updatedUser}")
                    _userState.value = result.updatedUser
                } else {
                    Log.e("Profile Update", "Profile update failed")
                }
            } catch (e: Exception) {
                Log.e("Profile Update", "Error: ${e.localizedMessage}")
                _message.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    private val _logoutState = MutableStateFlow<LogoutResponse?>(null)
    val logoutState: StateFlow<LogoutResponse?> get() = _logoutState

    private val _deleteState = MutableStateFlow<DeleteResponse?>(null)
    val deleteState: StateFlow<DeleteResponse?> get() = _deleteState

    fun logout() {
        viewModelScope.launch {
            try {
                val response = logoutUseCase()
                _logoutState.value = response
            } catch (e: Exception) {
                _logoutState.value = LogoutResponse(false, e.message ?: "Unknown error")
            }
        }
    }

    fun deleteAccount(userId: String) {
        viewModelScope.launch {
            try {
                val response = deleteAccountUseCase(userId)
                _deleteState.value = response
            } catch (e: Exception) {
                _deleteState.value = DeleteResponse(false, e.message ?: "Unknown error")
            }
        }
    }


}









