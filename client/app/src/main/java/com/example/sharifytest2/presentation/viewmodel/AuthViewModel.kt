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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val logoutUseCase : LogoutUseCase,

    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val userPreferences: UserPreferences // ✅ Inject UserPreferences
) : ViewModel()
{

    private val _authState = MutableStateFlow(AuthUiState())

    val authState: StateFlow<AuthUiState> = _authState





    init {
        viewModelScope.launch {
            val savedUserId = userPreferences.getUserId()
            Log.d("AuthViewModel", "✅ Retrieved user ID: $savedUserId from storage")
            _authState.update { it.copy(userId = savedUserId, userRole = userPreferences.getUserRole()) }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true) }

            try {
                val result = loginUseCase(LoginRequest(email, password))

                if (result.success) {
                    userPreferences.saveAuthToken(result.token ?: "")
                    userPreferences.saveUserRole(result.role ?: "user")
                    userPreferences.saveUserId(result.id ?: "") // ✅ Store user ID

                    _authState.update {
                        it.copy(success = true, userId = result.id, userRole = result.role)
                    }
                } else {
                    _authState.update { it.copy(error = result.message) }
                }

            } catch (e: Exception) {
                _authState.update { it.copy(error = e.localizedMessage) }
            }
        }
    }
    fun loadUserId() {
        viewModelScope.launch {
            val savedUserId = userPreferences.getUserId()
            println("🔍 Retrieved user ID from storage: $savedUserId") // ✅ Debugging
            _authState.update { it.copy(userId = savedUserId) } // ✅ Make sure UI updates
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
    fun updateProfile(userId: String, name: RequestBody, image: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                val result = updateProfileUseCase(userId, name, image)

                _message.value = result.message
                if (result.success) {
                    result.updatedUser?.let { updatedUser ->
                        _userState.value = updatedUser
                    }
                }
            } catch (e: Exception) {
                _message.value = "Update failed: ${e.localizedMessage}"
                Log.e("ProfileUpdate", "Error: ${e.stackTraceToString()}")
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










