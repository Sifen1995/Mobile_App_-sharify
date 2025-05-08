package com.example.sharifytest2.presentation.viewmodel


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharifytest2.domain.models.adminmodels.DeleteItemResponse

import com.example.sharifytest2.domain.models.adminmodels.Statistics
import com.example.sharifytest2.domain.models.userItemModel.Item
import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import com.example.sharifytest2.domain.useCase.adminItem.GetStatisticsUseCase
import com.example.sharifytest2.domain.useCase.adminItem.UpdateItemUseCase
import com.example.sharifytest2.domain.useCase.adminItem.DeleteItemUseCase
import com.example.sharifytest2.domain.useCase.userItem.GetItemDetailsUseCase
import com.example.sharifytest2.domain.useCase.userItem.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AdminItemViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val getStatisticsUseCase: GetStatisticsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    private val _itemDetail = MutableStateFlow<ItemDetail?>(null)
    val itemDetail: StateFlow<ItemDetail?> = _itemDetail.asStateFlow()

    private val _updateStatus = MutableStateFlow<String?>(null)
    val updateStatus: StateFlow<String?> = _updateStatus.asStateFlow()

    fun loadItems() {
        viewModelScope.launch {
            try {
                _items.value = getItemsUseCase.execute() ?: emptyList()
            } catch (e: Exception) {
                Log.e("AdminItemViewModel", "Error loading items", e)
            }
        }
    }

    fun fetchItemDetails(itemId: String) {
        viewModelScope.launch {
            try {
                _itemDetail.value = getItemDetailsUseCase.execute(itemId)
            } catch (e: Exception) {
                _updateStatus.value = "❌ Error fetching item details"
            }
        }
    }

    fun updateItem(
        itemId: String,
        name: String,
        smallDescription: String,
        description: String,
        termsAndConditions: String,
        telephone: String,
        address: String,
        isAvailable: Boolean,  // ✅ Add availability field
        imagePart: MultipartBody.Part?,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                _updateStatus.value = "Updating..."

                val updatedItem = updateItemUseCase.execute(
                    itemId = itemId,
                    name = name.toRequestBody(),
                    smallDescription = smallDescription.toRequestBody(),
                    description = description.toRequestBody(),
                    terms = termsAndConditions.toRequestBody(),
                    phone = telephone.toRequestBody(),
                    address = address.toRequestBody(),
                    isAvailable = isAvailable.toRequestBody(),  // ✅ Convert boolean to RequestBody
                    image = imagePart
                )

                _updateStatus.value = if (updatedItem != null) {
                    "✅ Item updated successfully"
                } else {
                    "❌ Update failed"
                }

                fetchItemDetails(itemId) // Refresh data
            } catch (e: Exception) {
                _updateStatus.value = "❌ Error: ${e.message}"
            }
        }
    }

    fun prepareImagePart(uri: Uri, context: Context): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: byteArrayOf()

        return MultipartBody.Part.createFormData(
            "image",
            "item_image.jpg",
            bytes.toRequestBody("image/*".toMediaTypeOrNull())
        )
    }

    private fun String.toRequestBody(): RequestBody {
        return this.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun Boolean.toRequestBody(): RequestBody {
        return this.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull()) // ✅ Converts Boolean to string
    }


    private val _statistics = MutableStateFlow<Statistics?>(null)
    val statistics: StateFlow<Statistics?> = _statistics

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadStatistics()
    }

    fun loadStatistics() {
        viewModelScope.launch {
            try {
                val statistics = getStatisticsUseCase.invoke()
                _statistics.value = statistics ?: Statistics.default() // ✅ Provide a default state
            } catch (e: Exception) {
                _error.value = "Failed to load statistics: ${e.message}" // ✅ Better error message
            }
        }
    }

    private val _deleteState = MutableStateFlow<DeleteItemResponse?>(null)
    val deleteState: StateFlow<DeleteItemResponse?> get() = _deleteState

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            _deleteState.value = deleteItemUseCase(itemId)
        }
    }
}


