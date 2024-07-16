package com.money.soypobre.data.repository

import com.money.soypobre.data.BudgetEntryDao
import com.money.soypobre.data.entity.BudgetEntity
import com.money.soypobre.data.entity.BudgetEntryEntity
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.model.BudgetEntry
import com.money.soypobre.domain.repository.BudgetEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetEntryRepositoryImpl @Inject constructor(
    private val dao: BudgetEntryDao
) : BudgetEntryRepository {
    override suspend fun insert(budgetEntries: List<BudgetEntry>) {
        dao.insert(budgetEntries.map(BudgetEntry::toEntity))
    }

    override suspend fun getAll(): Flow<Map<Budget, List<BudgetEntry>>> {
        return dao.getAll().map { map ->
            map.mapKeys { (key, _) ->
                key.toDomain()
            }.mapValues { (key, value) ->
                value.map { budgetEntry ->
                    budgetEntry.toDomain(key)
                }
            }
        }
    }
}

private fun BudgetEntry.toEntity() = BudgetEntryEntity(
    budgetId = budget.id,
    amount = amount,
    timestamp = timestamp,
    description = description
)

private fun BudgetEntryEntity.toDomain(budget: Budget) = BudgetEntry(
    id = id,
    budget = budget,
    amount = amount,
    timestamp = timestamp,
    description = description
)

private fun BudgetEntity.toDomain() = run {
    Budget(
        id,
        description,
        price,
        Budget.BudgetType.entries.getOrNull(type) ?: Budget.BudgetType.UNKNOWN
    )
}