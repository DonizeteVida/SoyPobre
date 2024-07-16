package com.money.soypobre.feature.entry.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.money.soypobre.R
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.ui.compose.DatePickerTextField
import com.money.soypobre.ui.compose.SimpleOutlinedDropDownMenu
import com.money.soypobre.ui.theme.SoyPobreTheme

private data class TabInfo(
    val type: Budget.BudgetType,
    @StringRes
    val title: Int,
    @StringRes
    val funnyTitle: Int,
    val icon: ImageVector
)

private val TAB_INFOS = mapOf(
    Budget.BudgetType.EXPENSE to TabInfo(
        Budget.BudgetType.EXPENSE,
        R.string.entry_expense_tab_title,
        R.string.entry_expense_tab_funny_title,
        Icons.Filled.Close
    ),
    Budget.BudgetType.EARNING to TabInfo(
        Budget.BudgetType.EARNING,
        R.string.entry_earning_tab_title,
        R.string.entry_earning_tab_funny_title,
        Icons.Filled.Add
    )
)

@Composable
fun EntryScreen(
    onEntryAdded: () -> Unit
) {
    val viewModel = hiltViewModel<EntryViewModel>()
    val state by viewModel.state.collectAsState()

    EntryScreenInner(
        state,
        onBudgetTypeChanged = viewModel::searchBudgetsForType,
        onEntryCompleted = { price: Double,
                             date: Long,
                             description: String,
                             budget: Budget ->
            viewModel.onEntryCompleted(price, date, description, budget, onEntryAdded)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EntryScreenInner(
    state: EntryViewModel.State,
    onBudgetTypeChanged: (Budget.BudgetType) -> Unit,
    onEntryCompleted: (
        price: Double,
        date: Long,
        description: String,
        budget: Budget
    ) -> Unit
) {
    val (selectedType, setSelectedType) = remember { mutableStateOf(Budget.BudgetType.EXPENSE) }
    val (price, setPrice) = remember { mutableStateOf("") }
    val (timestamp, setTimestamp) = remember { mutableLongStateOf(0) }
    val (description, setDescription) = remember { mutableStateOf("") }
    val (budgetIndex, setBudgetIndex) = remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            val info =
                TAB_INFOS[selectedType] ?: throw IllegalStateException("$selectedType not found")
            TopAppBar(
                title = { Text(stringResource(id = info.funnyTitle)) }
            )
        }
    ) { innerPadding ->

        LaunchedEffect(selectedType) {
            onBudgetTypeChanged(selectedType)
        }

        Column(
            Modifier
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedType.ordinal - 1) {
                for ((type, title, _, icon) in TAB_INFOS.values) {
                    Tab(
                        selected = selectedType == type,
                        onClick = { setSelectedType(type) }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(icon, contentDescription = null)
                            Text(stringResource(id = title))
                        }
                    }
                }
            }

            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(id = R.string.entry_input_money_hint)) },
                    leadingIcon = { Icon(Icons.Rounded.ShoppingCart, contentDescription = null) },
                    value = price,
                    onValueChange = setPrice,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                DatePickerTextField(
                    Modifier.fillMaxWidth(),
                    onTimestampSelected = setTimestamp
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = setDescription,
                    label = { Text(stringResource(id = R.string.entry_input_description_hint)) },
                    leadingIcon = { Icon(Icons.Rounded.Create, contentDescription = null) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleOutlinedDropDownMenu(
                    Modifier.fillMaxWidth(),
                    items = state.budgets,
                    asString = Budget::description,
                    value = state.budgets.getOrNull(budgetIndex)?.description ?: "",
                    onIndexChanged = setBudgetIndex
                )
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Button(onClick = {
                            onEntryCompleted(
                                price.toDoubleOrNull() ?: 0.0,
                                timestamp,
                                description,
                                state.budgets[budgetIndex]
                            )
                        }) {
                            Text(stringResource(id = R.string.entry_save_button))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EntryScreenPreview() {
    SoyPobreTheme {
        EntryScreenInner(
            state = EntryViewModel.State(),
            onBudgetTypeChanged = {

            },
            onEntryCompleted = { a, b, c, d ->

            }
        )
    }
}