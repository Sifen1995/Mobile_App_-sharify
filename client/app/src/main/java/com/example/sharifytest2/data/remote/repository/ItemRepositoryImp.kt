package com.example.sharifytest2.data.auth.repository

import com.example.sharifytest2.data.local.UserPreferences
import com.example.sharifytest2.data.model.Item.UpdateNoteDTO
import com.example.sharifytest2.data.model.lendItem.toDomainModel
import com.example.sharifytest2.data.remote.api.ItemApi
import com.example.sharifytest2.domain.models.adminmodels.AddItem
import com.example.sharifytest2.domain.models.adminmodels.DeleteItemResponse
import com.example.sharifytest2.domain.models.adminmodels.Statistics
import com.example.sharifytest2.domain.models.userItemModel.BorrowedItem
import com.example.sharifytest2.domain.models.userItemModel.Item
import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import com.example.sharifytest2.domain.repository.ItemRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: ItemApi,
    private val userPreferences: UserPreferences
) : ItemRepository {

    override suspend fun getItems(): List<Item> {
        val token = userPreferences.getAuthToken()
        val response = api.getItems("Bearer $token")
        return response.body()?.items
            ?.map { it.toDomainModel() }
            ?.filter { it.isAvailable } ?: emptyList()
    }

    override suspend fun getItemDetails(itemId: String): ItemDetail? {
        val response = api.getItemById(itemId)
        return response.body()?.item?.toDomainModel()
    }

    override suspend fun borrowItem(itemId: String): Boolean {
        return try {
            val token = userPreferences.getAuthToken()
            val response = api.borrowItem(itemId, "Bearer $token")
            response.isSuccessful && response.body()?.success == true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getBorrowedItems(): List<BorrowedItem> {
        return try {
            val token = userPreferences.getAuthToken()
            val response = api.getBorrowedItems("Bearer $token")
            val responseBody = response.body() ?: return emptyList()
            responseBody.borrowedItems.map { dto ->
                dto.toDomainModel().copy(image = fixImageUrl(dto.image))
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun fixImageUrl(rawUrl: String): String {
        return when {
            rawUrl.startsWith("/images") -> "http://10.0.2.2:4000$rawUrl"
            else -> rawUrl.replace("localhost", "10.0.2.2")
        }
    }

    override suspend fun updateNote(itemId: String, note: String): Boolean {
        return try {
            val token = userPreferences.getAuthToken() ?: return false
            val response = api.updateNote(
                authToken = "Bearer $token",
                id = itemId,
                request = UpdateNoteDTO(note)
            )
            response.isSuccessful && response.body()?.success == true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeBorrowedItem(itemId: String): Boolean {
        return try {
            val token = userPreferences.getAuthToken()
            val response = api.removeBorrowedItem("Bearer $token", itemId)
            response.isSuccessful && response.body()?.success == true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addItem(
        image: MultipartBody.Part,
        name: RequestBody,
        smalldescription: RequestBody,
        description: RequestBody,
        termsAndConditions: RequestBody,
        telephon: RequestBody,
        address: RequestBody
    ): AddItem {
        val response = api.addItem(
            image,
            name,
            smalldescription,
            description,
            termsAndConditions,
            telephon,
            address
        )
        val responseDTO = response.body()
        if (responseDTO?.success == true && responseDTO.item != null) {
            return responseDTO.item.toDomainModel()
        } else {
            throw Exception(responseDTO?.message ?: "Unknown error")
        }
    }

    override suspend fun updateItem(
        itemId: String,
        name: RequestBody?,
        smallDescription: RequestBody?,
        description: RequestBody?,
        terms: RequestBody?,
        phone: RequestBody?,
        address: RequestBody?,
        isAvailable: RequestBody?,
        image: MultipartBody.Part?
    ): ItemDetail? {
        return try {
            val response = api.updateItem(
                itemId,
                name,
                smallDescription,
                description,
                terms,
                phone,
                address,
                isAvailable,
                image
            )
            response.body()?.updatedItem?.toDomainModel()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getStatistics(): Statistics {
        val response = api.getStatistics()
        return if (response.isSuccessful) {
            response.body()?.statistics?.let { stats ->
                Statistics(
                    totalUsers = stats.totalUsers,
                    availableItems = stats.availableItems
                )
            } ?: Statistics.default()
        } else {
            Statistics.default()
        }
    }


    override suspend fun deleteItem(itemId: String): DeleteItemResponse {
        return try {
            val response = api.deleteItem(itemId)
            if (response.isSuccessful) {
                response.body()?.toDomainModel() ?: DeleteItemResponse(false, "Unexpected response format")
            } else {
                DeleteItemResponse(false, response.errorBody()?.string() ?: "Item deletion failed")
            }
        } catch (e: Exception) {
            DeleteItemResponse(false, e.message ?: "Unknown error")
        }
    }
}

