package com.example.sharifytest2.domain.models.auth

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String // should be like "/images/abc.png"
)