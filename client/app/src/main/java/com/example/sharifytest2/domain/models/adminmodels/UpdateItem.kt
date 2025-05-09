package com.example.sharifytest2.domain.models.adminmodels

data class UpdatedItem(
    val id: String,
    val image: String,
    val name: String,
    val smalldescription: String,
    val description: String,
    val termsAndConditions: String,
    val telephon: String,
    val address: String,
    val isAvailable: Boolean
)