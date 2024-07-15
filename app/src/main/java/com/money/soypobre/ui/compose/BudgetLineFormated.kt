package com.money.soypobre.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.money.soypobre.R
import com.money.soypobre.ui.theme.SoyPobreTheme

@Composable
fun BudgetLineFormated(
    category: String,
    price: Double,
    icon: @Composable (() -> Unit)? = null
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(category, Modifier.weight(1F))
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(id = R.string.app_money_format).format(price), Modifier.weight(1F))
        if (icon != null) {
            Spacer(modifier = Modifier.width(8.dp))
            icon()
        }
    }
}

@Preview
@Composable
private fun FormatedBudgetLinePreview() {
    SoyPobreTheme {
        BudgetLineFormated(category = "Shopping", price = 1250.00)
    }
}