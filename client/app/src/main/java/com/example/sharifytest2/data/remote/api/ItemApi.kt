package com.example.sharifytest2.data.remote.api

import com.example.sharifytest2.data.model.item.BorrowResponseDTO
import com.example.sharifytest2.data.model.item.BorrowedItemsResponseDTO
import com.example.sharifytest2.data.model.item.ItemDTO
import com.example.sharifytest2.data.model.item.ItemDetailDTO
import com.example.sharifytest2.data.model.item.ItemResponseDTO
import com.example.sharifytest2.data.model.item.RemoveItemResponseDTO
import com.example.sharifytest2.data.model.item.UpdateNoteDTO
import com.example.sharifytest2.data.model.item.UpdateNoteResponseDTO
import com.example.sharifytest2.data.model.lendItem.AddItemResponseDTO
import com.example.sharifytest2.data.model.lendItem.UpdateItemResponse
import com.example.sharifytest2.data.model.lendItem.UpdateItemResponseDTO
import com.example.sharifytest2.domain.Items.models.ItemsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ItemApi {
    @GET("api/admin/items")
    suspend fun getItems(@Header("Authorization") token: String): Response<ItemsResponse> // ✅ Pass token

    @GET("api/borrow/item/{id}")
    suspend fun getItemById(
        @Path("id") id: String // ✅ Use @Path to correctly pass ID in the URL
    ): Response<ItemResponseDTO> // ✅ Return ItemDetailDTO instead of ItemDTO for full details
    @PUT("api/borrow/borrow-item/{id}")

    suspend fun borrowItem(@Path("id") id: String, @Header("Authorization") token: String): Response<BorrowResponseDTO>


    @GET("api/borrow/borrowed-items")
    suspend fun getBorrowedItems(@Header("Authorization") token: String): Response<BorrowedItemsResponseDTO>


    @PUT("api/borrow/borrowed-item/note/{id}") // ✅ API route
    suspend fun updateNote(
        @Header("Authorization") authToken: String,
        @Path("id") id: String,
        @Body request: UpdateNoteDTO
    ): Response<UpdateNoteResponseDTO>

    @DELETE ("/api/borrow/borrowed-item/{id}")
    suspend fun removeBorrowedItem(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): Response<RemoveItemResponseDTO>

    @Multipart
    @POST("api/admin/add-Item")
    suspend fun addItem(

        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("smalldescription") smalldescription: RequestBody,
        @Part("description") description: RequestBody,
        @Part("termsAndConditions") termsAndConditions: RequestBody,
        @Part("telephon") telephon: RequestBody,
        @Part("address") address: RequestBody
    ): Response<AddItemResponseDTO>


    @Multipart
    @PUT("api/admin/items/{itemId}")
    suspend fun updateItem(
        @Path("itemId") itemId: String,
        @Part("name") name: RequestBody?,
        @Part("smallDescription") smallDescription: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("terms") terms: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Response<UpdateItemResponseDTO>







}