package com.example.sharifytest2.data.model.lendItem

import com.example.sharifytest2.domain.models.userItemModel.ItemDetail

data class UpdateItemResponseDTO(
    val success: Boolean,
    val message: String,
    val updatedItem: UpdatedItemDTO
)

data class UpdatedItemDTO(
    val id: String,
    val image: String,
    val name: String,
    val smalldescription: String,
    val description: String,
    val termsAndConditions: String,
    val telephon: String,
    val address: String,
    val isAvailable: Boolean
) {
    fun toDomainModel(): ItemDetail {
        return ItemDetail(
            id = id,
            name = name,
            smalldescription = smalldescription,
            description = description,
            termsAndConditions = termsAndConditions,
            telephon = telephon,
            address = address,
            image = image,
            isAvailable = isAvailable
        )
    }
}