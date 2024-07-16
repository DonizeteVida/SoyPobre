package com.money.soypobre.domain.usecase

import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.repository.BudgetRepository
import javax.inject.Inject

class GetBudgetsByTypeUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(
        type: Budget.BudgetType
    ) = budgetRepository.getAll(type)
}