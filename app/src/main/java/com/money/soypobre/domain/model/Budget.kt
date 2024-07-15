package com.money.soypobre.domain.model

data class Budget(
    val id: Long = 0,
    val description: String,
    val price: Double,
    val type: BudgetType
) {
    enum class BudgetType {
        UNKNOWN,
        EXPENSE,
        EARNING
    }
}