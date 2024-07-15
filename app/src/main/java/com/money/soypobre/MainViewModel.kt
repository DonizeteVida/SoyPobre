package com.money.soypobre

import androidx.lifecycle.ViewModel
import com.money.soypobre.domain.usecase.UserShouldOnboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userShouldOnboardUseCase: UserShouldOnboardUseCase
) : ViewModel() {
    val state = MutableStateFlow(State())

    suspend fun discoverUserStartDestination() {
        if (userShouldOnboardUseCase()) {
            state.update {
                it.copy(startDestination = Onboard)
            }
        }
        state.update { it.copy(isLoading = false) }
    }

    data class State(
        val isLoading: Boolean = true,
        val startDestination: Any = Home
    )
}