package com.money.soypobre.feature.onboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.domain.usecase.InsertBudgetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val insertBudgetsUseCase: InsertBudgetsUseCase
) : ViewModel() {
    val state = MutableStateFlow(State())

    fun updatePageState(
        firstName: String,
        lastName: String
    ) {
        state.update {
            it.copy(firstName = firstName, lastName = lastName)
        }
    }

    fun updatePageState(earnings: List<Budget>, expenses: List<Budget>) {
        state.update {
            it.copy(earnings = earnings, expenses = expenses)
        }
    }

    fun onUserConfirm(callback: () -> Unit) {
        state.update { it.copy(isLoading = true) }
        state.value.run {
            viewModelScope.launch {
                insertBudgetsUseCase(
                    expenses + earnings
                )
                callback()
            }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val firstName: String = "",
        val lastName: String = "",
        val expenses: List<Budget> = emptyList(),
        val earnings: List<Budget> = emptyList()
    )
}