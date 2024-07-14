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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.money.soypobre.R
import com.money.soypobre.ui.compose.BudgetLine
import com.money.soypobre.ui.theme.SoyPobreTheme
import kotlinx.coroutines.launch

private val steps: List<@Composable OnboardViewModel.(next: () -> Unit) -> Unit> = listOf(
    { next -> Page1(next) },
    { next -> Page2(next) },
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

    val (username, setUsername) = remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            stringResource(id = R.string.onboard_page_one_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )
        Text(
            stringResource(id = R.string.onboard_page_one_subtitle),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )
        Text(
            stringResource(id = R.string.onboard_page_one_users_name_question),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(stringResource(id = R.string.onboard_page_one_users_name_hint))
            },
            value = username,
            onValueChange = setUsername
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
                enabled = username.run { isNotBlank() && length > 10 },
                onClick = { updateUsername(username); next() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun OnboardViewModel.Page2(next: () -> Unit) {

    val earnings = remember { mutableStateListOf<Pair<String, String>>() }
    val expenses = remember { mutableStateListOf<Pair<String, String>>() }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboard_page_two_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )

        Column(
            Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(id = R.string.onboard_page_two_expense_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    Icons.Filled.Close,
                    tint = Color.Red,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            expenses.forEach {
                Text(it.toString())
            }
            BudgetLine(
                stringArrayResource(id = R.array.onboard_known_expense_categories)
            ) { category, description ->
                expenses += category to description
            }
        }

        Column(
            Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(id = R.string.onboard_page_two_earnings_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    Icons.Filled.Add,
                    tint = Color.Green,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            BudgetLine(
                stringArrayResource(id = R.array.onboard_known_earning_categories)
            ) { category, description ->
                earnings += category to description
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                enabled = true,
                onClick = { next() }
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

@Preview
@Composable
private fun PagePreview() {
    SoyPobreTheme {
        Scaffold { _ ->
            val viewModel: OnboardViewModel = hiltViewModel()
            viewModel.Page2 {

            }
        }
    }
}