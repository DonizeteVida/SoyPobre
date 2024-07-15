package com.money.soypobre.onboard.presentation

import com.money.soypobre.domain.model.Budget

data class OnboardState(
    val isLoading: Boolean = false,
    val username: String = "",
    val expenses: List<Budget> = emptyList(),
    val earnings: List<Budget> = emptyList()
)