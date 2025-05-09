package com.example.sharifytest2.domain.useCase.adminItem



import com.example.sharifytest2.domain.models.adminmodels.AddItem
import com.example.sharifytest2.domain.repository.ItemRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody


import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend fun execute(
        image: MultipartBody.Part,
        name: RequestBody,
        smalldescription: RequestBody,
        description: RequestBody,
        termsAndConditions: RequestBody,
        telephon: RequestBody,
        address: RequestBody
    ): AddItem {
        return itemRepository.addItem(image, name, smalldescription, description, termsAndConditions, telephon, address)
    }
}