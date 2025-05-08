package com.example.sharifytest2.domain.models.userItemModel


data class Item(
    val id: String,
    val image: String,
    val name: String,
    val smallDescription: String?,
    val isAvailable: Boolean
)