package com.money.soypobre.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.model.BudgetEntry
import com.money.soypobre.domain.repository.BudgetEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val budgetEntryRepository: BudgetEntryRepository
) : ViewModel() {
    val expenses = budgetEntryRepository
        .getAll(Budget.BudgetType.EXPENSE)
        .map {
            it.map { (budget, entries) ->
                BudgetHistory(
                    budget,
                    entries,
                    entries.sumOf(BudgetEntry::amount)
                )
            }
        }
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, emptyList())

    val earnings = budgetEntryRepository
        .getAll(Budget.BudgetType.EARNING)
        .map {
            it.map { (budget, entries) ->
                BudgetHistory(
                    budget,
                    entries,
                    entries.sumOf(BudgetEntry::amount)
                )
            }
        }
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, emptyList())

    class BudgetHistory(
        val budget: Budget,
        val entries: List<BudgetEntry>,
        val sum: Double
    )
}