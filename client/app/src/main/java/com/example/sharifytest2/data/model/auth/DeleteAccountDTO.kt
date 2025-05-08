package com.example.sharifytest2.data.model.auth

import com.example.sharifytest2.domain.models.auth.DeleteResponse

data class DeleteResponseDTO(
    val success: Boolean,
    val message: String
)

// ✅ Convert DTO to Domain Model
fun DeleteResponseDTO.toDomainModel(): DeleteResponse {
    return DeleteResponse(
        success = this.success,
        message = this.message
    )
}