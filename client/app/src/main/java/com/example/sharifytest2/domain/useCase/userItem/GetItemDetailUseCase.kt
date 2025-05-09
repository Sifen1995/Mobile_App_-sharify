package com.example.sharifytest2.domain.useCase.userItem

import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import com.example.sharifytest2.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemDetailsUseCase @Inject constructor(
    private val repository: ItemRepository
)
{
    suspend fun execute(itemId: String): ItemDetail? {
        return repository.getItemDetails(itemId) // ✅ Fetch data via repository
    }
}




