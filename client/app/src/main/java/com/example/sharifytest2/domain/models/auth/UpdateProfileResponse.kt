package com.example.sharifytest2.domain.models.auth

data class UpdateProfileResponse (
    val success:Boolean,
    val message:String,
    val updatedUser: User?

)