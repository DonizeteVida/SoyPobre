package com.money.soypobre.feature.onboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.feature.onboard.domain.usecase.CreateUserBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val createUserBudgetUseCase: CreateUserBudgetUseCase
) : ViewModel() {
    val state = MutableStateFlow(State())

    fun updateUsername(username: String) {
        state.update {
            it.copy(username = username)
        }
    }

    fun updateUserExpenses(expenses: List<Budget>) {
        state.update {
            it.copy(expenses = expenses)
        }
    }

    fun updateUserEarnings(earnings: List<Budget>) {
        state.update {
            it.copy(earnings = earnings)
        }
    }

    fun onUserConfirm(callback: () -> Unit) {
        state.update { it.copy(isLoading = true) }
        state.value.run {
            viewModelScope.launch {
                createUserBudgetUseCase(
                    earnings = earnings,
                    expenses = expenses
                )
                callback()
            }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val username: String = "",
        val expenses: List<Budget> = emptyList(),
        val earnings: List<Budget> = emptyList()
    )
}