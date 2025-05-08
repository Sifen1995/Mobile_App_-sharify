package com.example.sharifytest2.data.model.auth

import com.example.sharifytest2.domain.models.auth.User

data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String // should be like "/images/abc.png"
) {
    fun toDomain(): User {
        return User(
            id = id,
            name = name,
            email = email,
            profilePicture = fixImageUrl(profilePicture)
        )
    }


}
private fun fixImageUrl(rawUrl: String): String {
    return when {
        rawUrl.startsWith("/images") -> "http://10.0.2.2:4000$rawUrl"
        else -> rawUrl.replace("localhost", "10.0.2.2")
    }
}