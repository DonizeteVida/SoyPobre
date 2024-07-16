package com.money.soypobre.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.money.soypobre.R
import com.money.soypobre.ui.theme.SoyPobreTheme

@Composable
fun BudgetLineNewInsert(
    categories: Array<String> = emptyArray(),
    onAddClick: (description: String, price: Double) -> Unit
) {
    val (description, setDescription) = remember { mutableStateOf("") }
    val (price, setPrice) = remember { mutableStateOf("") }

    val (descriptionError, hasDescriptionError) = remember { mutableStateOf(false) }
    val (priceError, hasPriceError) = remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleOutlinedDropDownMenu(
            modifier = Modifier.weight(1F),
            items = categories.toList(),
            value = description,
            isError = descriptionError
        ) {
            setDescription(categories[it])
        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            leadingIcon = {
                Text(stringResource(id = R.string.app_money_symbol))
            },
            modifier = Modifier.weight(1F),
            isError = priceError,
            value = price,
            onValueChange = setPrice,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
                val descriptionError = description.isBlank()
                hasDescriptionError(descriptionError)

                val priceError = price.isBlank() ||
                        price.toDoubleOrNull() == null
                hasPriceError(priceError)

                if (descriptionError || priceError) {
                    return@IconButton
                }
                onAddClick(description, price.toDouble())
                setDescription("")
                setPrice("")
            }
        ) {
            RoundedIcon(icon = Icons.Rounded.Add)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BudgetLinePrev() {
    SoyPobreTheme {
        Column(
            Modifier.fillMaxSize()
        ) {
            BudgetLineNewInsert(
                arrayOf(
                    "Children",
                    "Shopping"
                )
            ) { a, b -> }
        }
    }
}