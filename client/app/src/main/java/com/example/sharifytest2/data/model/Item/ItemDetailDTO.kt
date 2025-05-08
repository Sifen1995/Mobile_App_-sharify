package com.example.sharifytest2.data.model.item

import com.example.sharifytest2.domain.models.userItemModel.ItemDetail
import com.google.gson.annotations.SerializedName

data class ItemDetailDTO(
    @SerializedName("_id") val id: String,
    val image: String,
    val name: String,
    val smalldescription: String?,
    val termsAndConditions: String,
    val description: String?,
    val isAvailable: Boolean,
    val telephon: String?,
    val address: String?
) {


    fun toDomainModel(): ItemDetail {
        return ItemDetail(
            id = this.id ?: "", // ✅ Ensure `id` is non-null
            image = this.image,
            name = this.name ?: "Unknown",
            smalldescription = this.smalldescription ?: "",
            termsAndConditions = this.termsAndConditions ?: "",
            description = this.description ?: "",
            isAvailable = this.isAvailable ?: false,
            telephon = this.telephon ?: "",
            address = this.address ?: ""
        )
    }
}