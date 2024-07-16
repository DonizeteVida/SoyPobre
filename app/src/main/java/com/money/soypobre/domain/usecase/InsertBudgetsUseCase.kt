package com.money.soypobre.domain.usecase

import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.repository.BudgetRepository
import javax.inject.Inject

class InsertBudgetsUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(
        earnings: List<Budget>,
        expenses: List<Budget>
    ) {
        budgetRepository.insert(earnings)
        budgetRepository.insert(expenses)
    }
}