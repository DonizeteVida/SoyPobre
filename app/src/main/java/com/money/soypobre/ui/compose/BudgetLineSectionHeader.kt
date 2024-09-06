package com.money.soypobre.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.money.soypobre.domain.model.Budget
import com.money.soypobre.ui.theme.SoyPobreTheme

@Composable
fun BudgetLineSectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    icon: @Composable () -> Unit,
    items: List<Budget>,
    drawItem: @Composable (index: Int, description: String, price: Double) -> Unit,
    trailing: @Composable () -> Unit = {}
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.width(16.dp))
            icon()
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(items) { index, (_, description, price) ->
                drawItem(index, description, price)
                if (index < items.lastIndex) {
                    HorizontalDivider()
                }
            }
            item { trailing() }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BudgetLineSectionHeaderPreview() {
    SoyPobreTheme {
        BudgetLineSectionHeader(
            title = "Gastos",
            icon = {
                Icon(
                    Icons.Filled.Close,
                    tint = Color.Red,
                    contentDescription = null
                )
            },
            items = List(10) {
                Budget(
                    id = it.toLong(),
                    description = "$it",
                    price = it.toDouble(),
                    type = Budget.BudgetType.EXPENSE
                )
            },
            drawItem = { _, category, price ->
                BudgetLineFormated(category = category, price = price)
            }
        )
    }
}