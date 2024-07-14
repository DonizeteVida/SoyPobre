package com.money.soypobre.onboard.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor() : ViewModel() {
    private val state = MutableStateFlow(OnboardState())

    fun updateUsername(username: String) {
        state.update {
            it.copy(username = username)
        }
    }
}