package com.example.sharifytest2.domain.models.userItemModel

import com.example.sharifytest2.data.model.item.ItemDetailDTO

data class ItemsDetailResponse(
    val items: List<ItemDetailDTO> // ✅ Wrap inside a response model
)
