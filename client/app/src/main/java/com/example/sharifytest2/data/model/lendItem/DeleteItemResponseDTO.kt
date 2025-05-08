package com.example.sharifytest2.data.model.lendItem

import com.example.sharifytest2.domain.models.adminmodels.DeleteItemResponse

data class DeleteItemResponseDTO(
    val success: Boolean,
    val message: String
)


fun DeleteItemResponseDTO.toDomainModel(): DeleteItemResponse? {
    return DeleteItemResponse(success, message)
}