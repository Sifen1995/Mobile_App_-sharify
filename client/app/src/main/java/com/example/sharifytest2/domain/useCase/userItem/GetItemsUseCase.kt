package com.example.sharifytest2.domain.useCase.userItem


import com.example.sharifytest2.domain.models.userItemModel.Item
import com.example.sharifytest2.domain.repository.ItemRepository


import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend fun execute(): List<Item> = repository.getItems()
}




