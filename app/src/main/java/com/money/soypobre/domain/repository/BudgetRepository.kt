package com.money.soypobre.domain.repository

import com.money.soypobre.domain.model.Budget

interface BudgetRepository {
    suspend fun insert(items: List<Budget>)
    suspend fun count(): Long
    suspend fun getAll(): List<Budget>
    suspend fun getAll(type: Budget.BudgetType): List<Budget>
}