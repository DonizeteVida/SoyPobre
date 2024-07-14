package com.money.soypobre.onboard.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.money.soypobre.ui.compose.map
import com.money.soypobre.ui.compose.update
import com.money.soypobre.ui.theme.SoyPobreTheme
import kotlinx.coroutines.launch

private val steps: List<@Composable OnboardViewModel.(next: () -> Unit) -> Unit> = listOf(
    { next -> Page1(next) },
    { next -> Text("Tela final") }
)

@Composable
fun OnboardScreen(
    viewModel: OnboardViewModel = hiltViewModel()
) {
    Scaffold { innerPadding ->
        val state = rememberPagerState { steps.size }
        val scope = rememberCoroutineScope()

        BackHandler(state.currentPage > 0) {
            scope.launch {
                state.animateScrollToPage(state.currentPage - 1)
            }
        }

        HorizontalPager(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            state = state,
            userScrollEnabled = false
        ) {
            steps[it].invoke(viewModel) {
                scope.launch {
                    state.animateScrollToPage(state.currentPage + 1)
                }
            }
        }
    }
}

@Composable
private fun OnboardViewModel.Page1(next: () -> Unit) {

    val username = remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Welcome to our onboard section",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )
        Text(
            "Let's meet first",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )
        Text(
            "What's your name?",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = username.update()
        )

        Spacer(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                enabled = username.map { length > 10 },
                onClick = { updateUsername(username.value); next() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardScreenPrev() {
    SoyPobreTheme {
        OnboardScreen()
    }
}