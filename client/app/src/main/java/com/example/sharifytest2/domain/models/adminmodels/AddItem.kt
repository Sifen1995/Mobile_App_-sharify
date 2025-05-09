package com.example.sharifytest2.domain.models.adminmodels




data class AddItem(
    val id: String,
    val name: String,
    val smalldescription: String,
    val description: String,
    val termsAndConditions: String,
    val telephon: String,
    val address: String,
    val image: String?,
    val isAvailable: Boolean
)