package com.money.soypobre.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.ui.theme.Seashell
import com.money.soypobre.ui.theme.SoyPobreTheme

@Composable
fun BudgetEditorSheet(
    modifier: Modifier = Modifier,
    title: String,
    options: List<String>,
    budgets: List<Budget>,
    onBudgetCreated: (option: Int, value: Double) -> Unit,
    onBudgetDeleted: (Int) -> Unit
) {
    val (dropDownValue, setDropDownValue) = remember { mutableIntStateOf(-1) }
    val (budgetValue, setBudgetValue) = remember { mutableStateOf("") }
    val scrollState = rememberScrollState(Int.MAX_VALUE)

    Column(modifier) {
        Text(
            title,
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 18.sp)
        )
        Spacer(Modifier.height(8.dp))
        Row {
            SimpleOutlinedDropDownMenu(
                Modifier.weight(1F),
                items = options,
                value = options.getOrNull(dropDownValue) ?: "",
                onIndexSelected = setDropDownValue
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                modifier = Modifier.weight(1F),
                value = budgetValue,
                onValueChange = setBudgetValue,
                singleLine = true,
                leadingIcon = { Text("R$") },
            )
        }
        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(top = 4.dp)
                .background(Seashell)
                .verticalScroll(scrollState)
        ) {
            budgets.forEachIndexed { index, budget ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        budget.description,
                        Modifier.weight(1F),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text("R$ %.2f".format(budget.price), Modifier.weight(1F))
                    IconButton(onClick = { onBudgetDeleted(index) }) {
                        Icon(Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
                    }
                }
                if (index + 1 < budgets.size) {
                    HorizontalDivider(
                        Modifier.padding(horizontal = 12.dp),
                        color = Color.Transparent
                    )
                }
            }
        }
        Button(
            enabled = dropDownValue != -1 && budgetValue.isNotBlank() && budgetValue.toDoubleOrNull() != null,
            onClick = {
                onBudgetCreated(dropDownValue, budgetValue.toDouble())
                setDropDownValue(-1)
                setBudgetValue("")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BudgetEditorSheetPrev() {
    SoyPobreTheme {
        Surface {
            Column(
                Modifier.padding(16.dp)
            ) {
                listOf("GANHOS", "PERDAS").forEach { title ->
                    BudgetEditorSheet(
                        modifier = Modifier.weight(1F),
                        title = title,
                        options = List(5) {
                            "$title: $it"
                        },
                        budgets = List(5) {
                            Budget(
                                it.toLong(),
                                "$title: $it",
                                it.toDouble(),
                                Budget.BudgetType.UNKNOWN
                            )
                        },
                        onBudgetCreated = { _, _ -> },
                        onBudgetDeleted = { }
                    )
                }
            }
        }
    }
}