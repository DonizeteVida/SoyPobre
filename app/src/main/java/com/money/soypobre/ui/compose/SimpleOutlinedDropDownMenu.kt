package com.money.soypobre.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.money.soypobre.ui.theme.SoyPobreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> SimpleOutlinedDropDownMenu(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    items: List<T>,
    asString: (T) -> String = Any::toString,
    value: String,
    onIndexSelected: (Int) -> Unit
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = setExpanded
    ) {
        OutlinedTextField(
            maxLines = 1,
            isError = isError,
            value = value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(asString(item)) },
                    onClick = {
                        onIndexSelected(index)
                        setExpanded(false)
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SimpleOutlinedDropDownMenuPrev() {
    SoyPobreTheme {
        Row(
            Modifier.fillMaxWidth()
        ) {
            SimpleOutlinedDropDownMenu(
                modifier = Modifier.fillMaxWidth(.5F),
                items = List(10) { it.toString() },
                value = "VALUE"
            ) {

            }
        }
    }
}