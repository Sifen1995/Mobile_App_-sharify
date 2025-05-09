package com.example.sharifytest2.di



import android.content.Context
import com.example.sharifytest2.data.auth.remote.AuthApi
import com.example.sharifytest2.data.auth.repository.ItemRepositoryImpl
import com.example.sharifytest2.data.local.UserPreferences
import com.example.sharifytest2.data.remote.api.ItemApi

import com.example.sharifytest2.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val cookieManager = java.net.CookieManager().apply {
            setCookiePolicy(java.net.CookiePolicy.ACCEPT_ALL)
        }

        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .cookieJar(okhttp3.JavaNetCookieJar(cookieManager))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideItemApi(retrofit: Retrofit): ItemApi { // 🚨 Incorrect: Should return `ItemApi`
        return retrofit.create(ItemApi::class.java)
    }

    @Provides
    @Singleton
    fun provideItemRepository(api: ItemApi, userPreferences: UserPreferences): ItemRepository {
        return ItemRepositoryImpl(api, userPreferences) // ✅ Ensure UserPreferences is injected
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }





}


