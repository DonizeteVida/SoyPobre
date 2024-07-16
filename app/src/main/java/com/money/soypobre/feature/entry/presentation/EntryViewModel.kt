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

    private fun async(callback: suspend () -> Unit) {
        state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            callback()
            state.update { it.copy(isLoading = false) }
        }
    }

    fun searchBudgetsForType(type: Budget.BudgetType) = async {
        getBudgetsByTypeUseCase(type).also { budgets ->
            state.update {
                it.copy(budgets = budgets)
            }
        }
    }

    fun onEntryCompleted(
        price: Double,
        date: Long,
        description: String,
        budget: Budget,
        onEntryAdded: () -> Unit
    ) = async {

    }

    data class State(
        val isLoading: Boolean = false,
        val budgets: List<Budget> = emptyList()
    )
}