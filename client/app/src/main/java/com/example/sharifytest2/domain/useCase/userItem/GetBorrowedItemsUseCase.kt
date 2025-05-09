package com.example.sharifytest2.domain.useCase.userItem



import com.example.sharifytest2.domain.models.userItemModel.BorrowedItem
import com.example.sharifytest2.domain.repository.ItemRepository
import javax.inject.Inject

class GetBorrowedItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository // ✅ Inject repository
) {
    suspend fun execute(): List<BorrowedItem> {
        return itemRepository.getBorrowedItems() // ✅ Fetch borrowed items
    }
}