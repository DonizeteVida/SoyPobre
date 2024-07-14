package com.money.soypobre.onboard.presentation

data class OnboardState(
    val username: String = "",
    val expenses: List<Pair<String, String>> = emptyList(),
    val earnings: List<Pair<String, String>> = emptyList()
)