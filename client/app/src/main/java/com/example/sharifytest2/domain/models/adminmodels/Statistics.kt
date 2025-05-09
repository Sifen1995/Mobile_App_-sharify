package com.example.sharifytest2.domain.models.adminmodels

data class Statistics(
    val totalUsers: Int,
    val availableItems: Int
){
    companion object {
        fun default(): Statistics {
            return Statistics(
                totalUsers = 0,

                availableItems = 0
            )
        }
    }
}



