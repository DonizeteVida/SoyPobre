package com.money.soypobre.ui.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.money.soypobre.R
import com.money.soypobre.ui.theme.SoyPobreTheme
import com.money.soypobre.util.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    modifier: Modifier = Modifier,
    onTimestampSelected: (Long) -> Unit = {}
) {
    val datePickerState = rememberDatePickerState(System.currentTimeMillis())
    val interactionSource = remember { MutableInteractionSource() }
    val isOutlineTextFieldPressed by interactionSource.collectIsPressedAsState()
    val (isDatePickerShowing, setDatePickerShowing) = remember { mutableStateOf(false) }

    val (value, setValue) = remember {
        mutableStateOf(DateFormatter.format(System.currentTimeMillis()))
    }

    LaunchedEffect(isOutlineTextFieldPressed) {
        if (isOutlineTextFieldPressed) {
            setDatePickerShowing(true)
        }
    }

    LaunchedEffect(Unit) {
        onTimestampSelected(datePickerState.selectedDateMillis ?: return@LaunchedEffect)
    }

    OutlinedTextField(
        interactionSource = interactionSource,
        modifier = modifier,
        readOnly = true,
        value = value,
        onValueChange = setValue,
        leadingIcon = {
            Icon(Icons.Filled.DateRange, contentDescription = "A calendar picker")
        }
    )

    if (isDatePickerShowing) {
        DatePickerDialog(
            onDismissRequest = { setDatePickerShowing(false) },
            confirmButton = {
                Button(onClick = {
                    val timestamp = datePickerState.selectedDateMillis ?: return@Button
                    onTimestampSelected(timestamp)
                    setValue(DateFormatter.format(timestamp))
                    setDatePickerShowing(false)
                }) {
                    Text(stringResource(id = R.string.app_calendar_confirm_button))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
private fun DatePickerTextFieldPreview() {
    SoyPobreTheme {
        DatePickerTextField()
    }
}