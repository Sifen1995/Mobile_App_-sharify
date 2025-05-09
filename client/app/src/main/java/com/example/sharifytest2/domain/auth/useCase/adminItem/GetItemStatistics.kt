package com.example.sharifytest2.domain.useCase.adminItem


import com.example.sharifytest2.domain.models.adminmodels.Statistics
import com.example.sharifytest2.domain.repository.ItemRepository


class GetStatisticsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(): Statistics? {
        return try {
            repository.getStatistics()
        } catch (e: Exception) {
            null // ✅ Return null instead of throwing an exception
        }
    }
}
