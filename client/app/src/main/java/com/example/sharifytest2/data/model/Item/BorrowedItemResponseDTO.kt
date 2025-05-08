package com.example.sharifytest2.data.model.item

data class BorrowedItemsResponseDTO(

    val borrowedItems: List<BorrowedItemDTO> // ✅ Return a list instead of a single object
)
