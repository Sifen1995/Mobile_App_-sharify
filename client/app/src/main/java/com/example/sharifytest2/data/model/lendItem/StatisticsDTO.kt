package com.example.sharifytest2.data.model.lendItem


data class StatisticsResponseDto(
    val success: Boolean,
    val statistics: StatisticsDto
)


data class StatisticsDto(
    val totalUsers: Int,
    val availableItems: Int
)
