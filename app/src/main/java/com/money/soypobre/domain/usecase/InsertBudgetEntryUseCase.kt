package com.money.soypobre.domain.usecase

import com.money.soypobre.domain.model.BudgetEntry
import com.money.soypobre.domain.repository.BudgetEntryRepository
import javax.inject.Inject

class InsertBudgetEntryUseCase @Inject constructor(
    private val budgetEntryRepository: BudgetEntryRepository
) {
    suspend operator fun invoke(
        budgetEntry: BudgetEntry
    ) {
        budgetEntryRepository.insert(listOf(budgetEntry))
    }
}