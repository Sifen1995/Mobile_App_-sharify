package com.example.sharifytest2.di


import com.example.sharifytest2.domain.useCase.adminItem.GetStatisticsUseCase
import com.example.sharifytest2.domain.repository.ItemRepository
import com.example.sharifytest2.domain.useCase.userItem.GetItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ItemModule {

    @Provides
    fun provideGetItemsUseCase(repository: ItemRepository): GetItemsUseCase {
        return GetItemsUseCase(repository)
    }

    @Provides

    fun provideGetStatisticsUseCase(
        repository: ItemRepository
    ): GetStatisticsUseCase {
        return GetStatisticsUseCase(repository)
    }
}