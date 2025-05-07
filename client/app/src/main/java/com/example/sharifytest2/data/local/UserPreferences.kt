package com.example.sharifytest2.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ROLE = "user_role"
    }

    // ✅ Cached values to reduce repeated SharedPreferences calls
    private var cachedToken: String? = null
    private var cachedRole: String? = null

    // ✅ Retrieve token with caching
    fun getAuthToken(): String {
        if (cachedToken == null) {
            cachedToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "") ?: ""
            println("🔍 Retrieved token from storage: $cachedToken")
        }
        return cachedToken!!
    }

    fun saveAuthToken(token: String) {
        cachedToken = token // ✅ Cache value
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
        println("✅ Token stored in SharedPreferences: $token")
    }

    // ✅ Retrieve user role with caching
    fun getUserRole(): String {
        if (cachedRole == null) {
            cachedRole = sharedPreferences.getString(KEY_USER_ROLE, "user") ?: "user"
            println("🔍 Retrieved user role from storage: $cachedRole")
        }
        return cachedRole!!
    }

    fun saveUserRole(role: String) {
        cachedRole = role // ✅ Cache value
        sharedPreferences.edit().putString(KEY_USER_ROLE, role).apply()
        println("✅ User role stored in SharedPreferences: $role")
    }

    // ✅ Optional: Clear preferences if needed
    fun clearUserData() {
        cachedToken = null
        cachedRole = null
        sharedPreferences.edit().clear().apply()
        println("⚠️ User data cleared from SharedPreferences")
    }
}