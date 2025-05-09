package com.example.sharifytest2.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_USER_ID = "user_id"

    }

    private var cachedToken: String? = null
    private var cachedRole: String? = null
    private var cachedUserId: String? = null // ✅ Added caching for user ID

    // ✅ Get User ID with caching
    fun getUserId(): String? {
        if (cachedUserId == null) {
            cachedUserId = sharedPreferences.getString(KEY_USER_ID, null)
            Log.d("UserPreferences", "🔍 Retrieved user ID from storage: $cachedUserId")
        }
        return cachedUserId
    }

    // ✅ Save User ID and log confirmation
    fun saveUserId(userId: String) {
        cachedUserId = userId
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
        Log.d("UserPreferences", "✅ User ID stored: $userId")
    }

    // ✅ Retrieve token with caching
    fun getAuthToken(): String {
        if (cachedToken == null) {
            cachedToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "") ?: ""
            Log.d("UserPreferences", "🔍 Retrieved token from storage: $cachedToken")
        }
        return cachedToken!!
    }

    fun saveAuthToken(token: String) {
        cachedToken = token
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
        Log.d("UserPreferences", "✅ Token stored in SharedPreferences: $token")
    }

    // ✅ Retrieve user role with caching
    fun getUserRole(): String {
        if (cachedRole == null) {
            cachedRole = sharedPreferences.getString(KEY_USER_ROLE, "user") ?: "user"
            Log.d("UserPreferences", "🔍 Retrieved user role from storage: $cachedRole")
        }
        return cachedRole!!
    }

    fun saveUserRole(role: String) {
        cachedRole = role
        sharedPreferences.edit().putString(KEY_USER_ROLE, role).apply()
        Log.d("UserPreferences", "✅ User role stored in SharedPreferences: $role")
    }



    // ✅ Clear cached data to prevent stale values
    fun clearUserData() {
        cachedToken = null
        cachedRole = null
        cachedUserId = null
        sharedPreferences.edit().clear().apply()
        Log.d("UserPreferences", "⚠️ User data cleared from SharedPreferences")
    }
}