package com.example.sharifytest2.domain.repository




import com.example.sharifytest2.domain.models.adminmodels.AddItem
import com.example.sharifytest2.domain.models.adminmodels.DeleteItemResponse
import com.example.sharifytest2.domain.models.adminmodels.Statistics
import com.example.sharifytest2.domain.models.userItemModel.BorrowedItem
import com.example.sharifytest2.domain.models.userItemModel.Item
import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ItemRepository {
    suspend fun getItems(): List<Item>
    suspend fun getItemDetails(itemId: String): ItemDetail? // ✅ Ensure this matches the override
    suspend fun borrowItem(itemId: String): Boolean
    suspend fun getBorrowedItems(): List<BorrowedItem>
    suspend fun updateNote(itemId: String, note: String): Boolean
    suspend fun removeBorrowedItem(itemId: String): Boolean
    suspend fun addItem(
        image: MultipartBody.Part,
        name: RequestBody,
        smalldescription: RequestBody,
        description: RequestBody,
        termsAndConditions: RequestBody,
        telephon: RequestBody,
        address: RequestBody

    ): AddItem


    suspend fun updateItem(
        itemId: String,
        name: RequestBody? = null,
        smallDescription: RequestBody? = null,
        description: RequestBody? = null,
        terms: RequestBody? = null,
        phone: RequestBody? = null,
        address: RequestBody? = null,
        isAvailable: RequestBody? = null, // ✅ Added availability
        image: MultipartBody.Part? = null
    ): ItemDetail?


    suspend fun getStatistics(): Statistics
    suspend fun deleteItem(itemId: String): DeleteItemResponse
}



