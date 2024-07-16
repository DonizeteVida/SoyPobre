package com.money.soypobre.feature.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.money.soypobre.R
import com.money.soypobre.ui.theme.SoyPobreTheme

@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    onSettings: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = null)
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onFabClick
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.home_page_welcome_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.home_page_welcome_subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    SoyPobreTheme {
        HomeScreen(
            onFabClick = {

            },
            onSettings = {

            }
        )
    }
}