package com.example.sharifytest2.presentation.viewmodel


import android.content.Context
import android.net.Uri
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharifytest2.data.local.UserPreferences
import com.example.sharifytest2.domain.models.adminmodels.AddItem
import com.example.sharifytest2.domain.models.userItemModel.BorrowedItem
import com.example.sharifytest2.domain.models.userItemModel.Item
import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import com.example.sharifytest2.domain.useCase.adminItem.AddItemUseCase
import com.example.sharifytest2.domain.useCase.userItem.BorrowItemUseCase
import com.example.sharifytest2.domain.useCase.userItem.GetBorrowedItemsUseCase
import com.example.sharifytest2.domain.useCase.userItem.GetItemDetailsUseCase
import com.example.sharifytest2.domain.useCase.userItem.GetItemsUseCase
import com.example.sharifytest2.domain.useCase.userItem.RemoveBorrowedItemUseCase
import com.example.sharifytest2.domain.useCase.userItem.UpdateNoteUseCase
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val borrowItemUseCase: BorrowItemUseCase,
    private val getItemsUseCase: GetItemsUseCase,
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val updateNoteUseCase : UpdateNoteUseCase,
    private val getBorrowedItemsUseCase: GetBorrowedItemsUseCase,
    private val removeBorrowedItemUseCase: RemoveBorrowedItemUseCase,
    private val userPreferences: UserPreferences,
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {


    private val _itemDetail = MutableStateFlow<ItemDetail?>(null)
    val itemDetail: StateFlow<ItemDetail?> = _itemDetail

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items



    fun loadItems() {
        viewModelScope.launch {
            try {
                val fetchedItems = getItemsUseCase.execute() ?: emptyList() // ✅ Handle null values
                Log.d("ItemViewModel", "✅ Fetched items count: ${fetchedItems.size}")
                _items.emit(fetchedItems) // ✅ Update asynchronously
            } catch (e: Exception) {
                Log.e("ItemViewModel", "❌ Error fetching items: ${e.message}", e)
            }
        }
    }