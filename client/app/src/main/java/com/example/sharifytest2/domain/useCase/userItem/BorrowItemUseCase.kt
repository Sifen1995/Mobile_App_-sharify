package com.example.sharifytest2.domain.useCase.userItem

import com.example.sharifytest2.domain.repository.ItemRepository
import javax.inject.Inject

class BorrowItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository // ✅ Inject repository
) {
    suspend fun execute(itemId: String): Boolean {
        return itemRepository.borrowItem(itemId) // ✅ Fetch from repository
    }
}