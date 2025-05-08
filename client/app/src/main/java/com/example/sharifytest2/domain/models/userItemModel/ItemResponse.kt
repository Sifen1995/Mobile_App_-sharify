package com.example.sharifytest2.domain.models.userItemModel

import com.example.sharifytest2.data.model.item.ItemDTO

data class ItemsResponse(
    val items: List<ItemDTO> // ✅ Wrap inside a response model
)
