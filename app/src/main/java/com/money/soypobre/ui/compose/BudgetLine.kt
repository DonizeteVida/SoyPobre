package com.money.soypobre.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.money.soypobre.ui.theme.SoyPobreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetLine(
    categories: Array<String> = emptyArray(),
    onAddClick: (category: String, description: String) -> Unit
) {
    val (category, setCategory) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    val (categoryError, hasCategoryError) = remember { mutableStateOf(false) }
    val (descriptionError, hasDescriptionError) = remember { mutableStateOf(false) }

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
                isError = categoryError,
                value = category,
                onValueChange = setCategory,
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
                        modifier = Modifier.fillMaxWidth(),
                        text = {
                            Text(category)
                        },
                        onClick = {
                            setCategory(category)
                            setExpanded(false)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            modifier = Modifier.weight(1F),
            isError = descriptionError,
            value = description,
            onValueChange = setDescription
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
                val categoryError = category
                    .isBlank()
                hasCategoryError(categoryError)

                val descriptionError = description
                    .run { isBlank() || length < 8 }
                hasDescriptionError(descriptionError)

                if (categoryError || descriptionError) {
                    return@IconButton
                }
                onAddClick(category, description)
                setCategory("")
                setDescription("")
            }
        ) {
            RoundedIcon(icon = Icons.Rounded.Add)
        }
    }
}

@Preview
@Composable
private fun BudgetLinePrev() {
    SoyPobreTheme {
        BudgetLine { a, b -> }
    }
}