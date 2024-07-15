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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
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
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.ui.compose.BudgetLineFormated
import com.money.soypobre.ui.compose.BudgetLineNewInsert
import com.money.soypobre.ui.compose.BudgetLineSectionHeader
import com.money.soypobre.ui.theme.SoyPobreTheme
import kotlinx.coroutines.launch

private val steps: List<@Composable OnboardViewModel.(next: () -> Unit) -> Unit> = listOf(
    { next -> Page1(next) },
    { next -> Page2(next) },
    { next -> Page3(next) }
)

@Composable
fun OnboardScreen(
    viewModel: OnboardViewModel = hiltViewModel(),
    onFinish: () -> Unit
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
                if (it + 1 >= state.pageCount) {
                    onFinish()
                } else {
                    scope.launch {
                        state.animateScrollToPage(state.currentPage + 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardViewModel.Page1(next: () -> Unit) {

    val state by state.collectAsState()
    val (username, setUsername) = remember { mutableStateOf(state.username) }

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
                onClick = {
                    updateUsername(username)
                    next()
                }
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

    val state by state.collectAsState()
    val earnings = remember { state.earnings.toMutableStateList() }
    val expenses = remember { state.expenses.toMutableStateList() }

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

        Spacer(modifier = Modifier.height(16.dp))

        BudgetLineSectionHeader(
            Modifier
                .fillMaxWidth()
                .weight(1F),
            title = stringResource(id = R.string.onboard_page_two_earnings_title),
            icon = {
                Icon(
                    Icons.Filled.Add,
                    tint = Color.Green,
                    contentDescription = null
                )
            },
            items = earnings,
            drawItem = { index, category, price ->
                BudgetLineFormated(category = category, price = price) {
                    IconButton(onClick = { earnings.removeAt(index) }) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
            },
            trailing = {
                BudgetLineNewInsert(
                    stringArrayResource(id = R.array.onboard_known_earning_categories)
                ) { description, price ->
                    earnings += Budget(
                        description = description,
                        price = price,
                        type = Budget.BudgetType.EARNING
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BudgetLineSectionHeader(
            Modifier
                .fillMaxWidth()
                .weight(1F),
            title = stringResource(id = R.string.onboard_page_two_expense_title),
            icon = {
                Icon(
                    Icons.Filled.Close,
                    tint = Color.Red,
                    contentDescription = null
                )
            },
            items = expenses,
            drawItem = { index, category, price ->
                BudgetLineFormated(category = category, price = price) {
                    IconButton(onClick = { expenses.removeAt(index) }) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
            },
            trailing = {
                BudgetLineNewInsert(
                    stringArrayResource(id = R.array.onboard_known_expense_categories)
                ) { description, price ->
                    expenses += Budget(
                        description = description,
                        price = price,
                        type = Budget.BudgetType.EXPENSE
                    )
                }
            }
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                enabled = expenses.isNotEmpty() && earnings.isNotEmpty(),
                onClick = {
                    updateUserExpenses(expenses)
                    updateUserEarnings(earnings)
                    next()
                }
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
private fun OnboardViewModel.Page3(next: () -> Unit) {
    val state by state.collectAsState()
    val isLoading = state.isLoading
    val expenses = state.expenses
    val earnings = state.earnings

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboard_page_three_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.onboard_page_three_username_confirm).format(state.username),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        BudgetLineSectionHeader(
            Modifier
                .fillMaxWidth()
                .weight(1F),
            title = stringResource(id = R.string.onboard_page_two_earnings_title),
            icon = {
                Icon(
                    Icons.Filled.Add,
                    tint = Color.Green,
                    contentDescription = null
                )
            },
            items = earnings,
            drawItem = { _, category, price ->
                BudgetLineFormated(category = category, price = price)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BudgetLineSectionHeader(
            Modifier
                .fillMaxWidth()
                .weight(1F),
            title = stringResource(id = R.string.onboard_page_two_expense_title),
            icon = {
                Icon(
                    Icons.Filled.Close,
                    tint = Color.Red,
                    contentDescription = null
                )
            },
            items = expenses,
            drawItem = { _, category, price ->
                BudgetLineFormated(category = category, price = price)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.onboard_page_three_confirmation))
        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "->"
                )

                IconButton(
                    onClick = { onUserConfirm(next) }
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        tint = Color.Red,
                        contentDescription = null
                    )
                }

                Text(
                    text = "<-"
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardScreenPrev() {
    SoyPobreTheme {
        OnboardScreen {

        }
    }
}