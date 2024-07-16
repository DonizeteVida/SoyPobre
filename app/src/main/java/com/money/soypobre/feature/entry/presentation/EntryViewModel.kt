package com.money.soypobre.feature.entry.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.usecase.GetBudgetsByTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val getBudgetsByTypeUseCase: GetBudgetsByTypeUseCase
) : ViewModel() {
    val state = MutableStateFlow(State())

    fun searchBudgetsForType(type: Budget.BudgetType) {
        state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getBudgetsByTypeUseCase(type).also { budgets ->
                state.update {
                    it.copy(budgets = budgets)
                }
            }
            state.update { it.copy(isLoading = false) }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val budgets: List<Budget> = emptyList()
    )
}