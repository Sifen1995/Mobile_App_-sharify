package com.example.sharifytest2.domain.useCase.adminItem



import com.example.sharifytest2.domain.models.adminmodels.DeleteItemResponse
import com.example.sharifytest2.domain.repository.ItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor (private val repository: ItemRepository) {
    suspend operator fun invoke(itemId: String): DeleteItemResponse {
        return repository.deleteItem(itemId)
    }
}