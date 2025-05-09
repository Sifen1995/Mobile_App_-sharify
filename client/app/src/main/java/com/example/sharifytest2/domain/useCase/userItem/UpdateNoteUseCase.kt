package com.example.sharifytest2.domain.useCase.userItem

import com.example.sharifytest2.domain.repository.ItemRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val itemRepository: ItemRepository // ✅ Inject repository
) {
    suspend fun execute(itemId: String, note: String): Boolean {
        return itemRepository.updateNote(itemId, note) // ✅ Forward note update request
    }
}