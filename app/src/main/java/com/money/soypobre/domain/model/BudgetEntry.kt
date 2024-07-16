package com.money.soypobre.domain.model

data class BudgetEntry(
    val id: Long = 0,
    val budget: Budget,
    val amount: Double,
    val timestamp: Long,
    val description: String
)