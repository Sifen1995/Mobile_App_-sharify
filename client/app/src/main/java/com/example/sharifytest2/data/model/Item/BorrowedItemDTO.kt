package com.example.sharifytest2.data.model.item



import com.example.sharifytest2.domain.models.userItemModel.BorrowedItem

data class BorrowedItemDTO(
    val id: String,
    val image: String,
    val name: String,
    val smalldescription: String?,
    val note: String?
) {
    fun toDomainModel(): BorrowedItem {
        return BorrowedItem(
            id = this.id,
            image = this.image,
            name = this.name,
            smalldescription = this.smalldescription ?: "",
            note = this.note ?: ""
        )
    }
}
