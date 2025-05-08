package com.example.sharifytest2.data.model.auth

import com.example.sharifytest2.domain.models.auth.LogoutResponse

data class LogoutResponseDTO(
    val success: Boolean,
    val message: String
)

// ✅ Properly defined extension function (outside the class)
fun LogoutResponseDTO.toDomainModel(): LogoutResponse {
    return LogoutResponse(
        success = this.success,
        message = this.message
    )
}