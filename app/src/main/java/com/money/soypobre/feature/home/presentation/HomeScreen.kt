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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.money.soypobre.R
import com.money.soypobre.ui.theme.SoyPobreTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.shape.Shape

@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    onSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    val earnings by viewModel.earnings.collectAsState()

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
            Spacer(modifier = Modifier.height(16.dp))
            if (expenses.isNotEmpty()) {
                GroupedBarChart(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.home_page_expense_chart_title),
                    histories = expenses
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (earnings.isNotEmpty()) {
                GroupedBarChart(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.home_page_earning_chart_title),
                    histories = earnings
                )
            }
        }
    }
}

@Composable
private fun GroupedBarChart(
    modifier: Modifier,
    title: String,
    histories: List<HomeViewModel.BudgetHistory>
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val shape = remember { Shape.Rectangle }

    LaunchedEffect(histories) {
        modelProducer.runTransaction {
            columnSeries {
                series(histories.map { it.budget.price })
                series(histories.map { it.sum })
            }
        }
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title)
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(
                            color = Color.Red,
                            thickness = 25.dp,
                            shape = shape
                        ),
                        rememberLineComponent(
                            color = Color.Green,
                            thickness = 25.dp,
                            shape = shape
                        )
                    )
                ),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(valueFormatter = { x, _, _ ->
                    histories[x.toInt()].budget.description
                })
            ),
            modelProducer = modelProducer,
            modifier = modifier,
            zoomState = rememberVicoZoomState(zoomEnabled = false),
            runInitialAnimation = true
        )
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