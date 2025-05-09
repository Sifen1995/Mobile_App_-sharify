package com.example.sharifytest2.presentation.uiStates

data class AuthUiState(
    val userId: String? = null,

    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val userRole: String? = "person", // Default role is "person"
    val profileImageUrl: String? = null // 🔥 Now properly initialized!
)