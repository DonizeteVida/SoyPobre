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

    override fun getAll(type: Budget.BudgetType): Flow<List<Pair<Budget, List<BudgetEntry>>>> {
        return dao.getAll(type.ordinal).map { all ->
            all.map {
                val budget = it.key.toDomain()
                val entries = it.value.map { entry -> entry.toDomain(budget) }
                budget to entries
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