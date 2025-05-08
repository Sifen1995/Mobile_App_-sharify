package com.example.sharifytest2.data.model.item

data class ItemResponseDTO(
    val success: Boolean,
    val item: ItemDetailDTO? // ✅ Wrap inside an object to match API response structure
)