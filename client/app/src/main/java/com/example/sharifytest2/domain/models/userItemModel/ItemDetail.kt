package com.example.sharifytest2.domain.models.userItemModel

data class ItemDetail(
    val id: String?,
    val image: String,
    val name: String,
    val smalldescription: String?,
    val termsAndConditions: String?, // ✅ Allow nullable value
    val description: String?,
    val isAvailable: Boolean?,
    val telephon: String?,
    val address: String?
)