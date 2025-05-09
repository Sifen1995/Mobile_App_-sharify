package com.example.sharifytest2.domain.useCase.adminItem


import android.util.Log
import javax.inject.Inject
import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import com.example.sharifytest2.domain.repository.ItemRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UpdateItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend fun execute(
        itemId: String,
        name: RequestBody? = null,
        smallDescription: RequestBody? = null,
        description: RequestBody? = null,
        terms: RequestBody? = null,
        phone: RequestBody? = null,
        address: RequestBody? = null,
        isAvailable: RequestBody? = null, // ✅ Added Availability
        image: MultipartBody.Part? = null
    ): ItemDetail? {
        return try {
            itemRepository.updateItem(itemId, name, smallDescription, description, terms, phone, address, isAvailable, image)
        } catch (e: Exception) {
            Log.e("UpdateItemUseCase", "❌ Error updating item: ${e.message}", e)
            null // ✅ Return null instead of throwing an exception
        }
    }
}