package com.money.soypobre.data.repository

import com.money.soypobre.data.BudgetDao
import com.money.soypobre.data.entity.BudgetEntity
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.repository.BudgetRepository
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val dao: BudgetDao
) : BudgetRepository {
    override suspend fun count(): Long {
        return dao.count()
    }

    override suspend fun insert(items: List<Budget>) {
        dao.insert(items.map(Budget::toEntity))
    }

    override suspend fun getAll(): List<Budget> {
        return dao.getAll().map(BudgetEntity::toDomain)
    }

    override suspend fun getAll(type: Budget.BudgetType): List<Budget> {
        return dao.getAll(type.ordinal).map(BudgetEntity::toDomain)
    }
}

private fun Budget.toEntity() = run {
    BudgetEntity(
        id,
        description,
        price,
        type.ordinal
    )
}

private fun BudgetEntity.toDomain() = run {
    Budget(
        id,
        description,
        price,
        Budget.BudgetType.entries.getOrNull(type) ?: Budget.BudgetType.UNKNOWN
    )
}