package com.money.soypobre.domain.repository

import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.model.BudgetEntry
import kotlinx.coroutines.flow.Flow

interface BudgetEntryRepository {
    suspend fun insert(budgetEntries: List<BudgetEntry>)
    fun getAll(type: Budget.BudgetType): Flow<List<Pair<Budget, List<BudgetEntry>>>>
}