package com.money.soypobre.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetLineNewInsert(
    categories: Array<String> = emptyArray(),
    onAddClick: (description: String, price: Double) -> Unit
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    val (description, setDescription) = remember { mutableStateOf("") }
    val (price, setPrice) = remember { mutableStateOf("") }

    val (descriptionError, hasDescriptionError) = remember { mutableStateOf(false) }
    val (priceError, hasPriceError) = remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(1F),
            expanded = expanded,
            onExpandedChange = setExpanded
        ) {
            OutlinedTextField(
                maxLines = 1,
                isError = descriptionError,
                value = description,
                onValueChange = setDescription,
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { setExpanded(false) }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(category)
                        },
                        onClick = {
                            setDescription(category)
                            setExpanded(false)
                        }
                    )
                }
            }
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
        BudgetLineNewInsert(
            arrayOf(
                "Children",
                "Shopping"
            )
        ) { a, b -> }
    }
}