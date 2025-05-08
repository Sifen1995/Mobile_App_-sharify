package com.example.sharifytest2.data.model.item

import com.example.sharifytest2.domain.models.userItemModel.Item



data class ItemDTO(
    val id: String,
    val image: String,
    val name: String,
    val smalldescription: String?,
    val isAvailable: Boolean,
) {
    fun toDomainModel(): Item {
        return Item(id, image, name, smalldescription,isAvailable)
    }
}