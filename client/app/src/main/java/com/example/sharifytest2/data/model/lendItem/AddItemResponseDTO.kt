package com.example.sharifytest2.data.model.lendItem

import com.example.sharifytest2.domain.models.adminmodels.AddItem

data class AddItemResponseDTO(
    val success: Boolean,
    val message: String,
    val item: AddItemDTO
)

data class AddItemDTO(
    val id: String,
    val image: String,
    val name: String,
    val smalldescription: String,
    val description: String, // ✅ Added missing fields
    val termsAndConditions: String,
    val telephon: String,
    val address: String,
    val isAvailable: Boolean
) {
    fun toDomainModel(): AddItem {
        return AddItem(
            id = this.id,
            name = this.name,
            smalldescription = this.smalldescription,
            description = this.description, // ✅ Now using actual data
            termsAndConditions = this.termsAndConditions,
            telephon = this.telephon,
            address = this.address,
            image = this.image,
            isAvailable = this.isAvailable
        )
    }
}