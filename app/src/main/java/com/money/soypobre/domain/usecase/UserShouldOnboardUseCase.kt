package com.money.soypobre.domain.usecase

import com.money.soypobre.domain.repository.BudgetRepository
import javax.inject.Inject

class UserShouldOnboardUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke() = budgetRepository.count() <= 0
}