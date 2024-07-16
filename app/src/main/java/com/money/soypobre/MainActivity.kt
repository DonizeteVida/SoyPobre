package com.money.soypobre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.money.soypobre.feature.entry.presentation.EntryScreen
import com.money.soypobre.feature.home.presentation.HomeScreen
import com.money.soypobre.feature.onboard.presentation.OnboardScreen
import com.money.soypobre.ui.theme.SoyPobreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { mainViewModel.state.value.isLoading }

        enableEdgeToEdge()
        setContent {
            SoyPobreTheme {
                val navController = rememberNavController()
                val state by mainViewModel.state.collectAsState()

                LaunchedEffect(navController) {
                    mainViewModel.discoverUserStartDestination()
                }

                NavHost(
                    navController,
                    state.startDestination
                ) {
                    composable<Onboard> {
                        OnboardScreen {
                            navController.navigate(Home) {
                                popUpTo(0)
                            }
                        }
                    }
                    composable<Home> {
                        HomeScreen(
                            onSettings = {

                            },
                            onFabClick = {
                                navController.navigate(Entry)
                            }
                        )
                    }
                    composable<Entry> {
                        EntryScreen()
                    }
                }
            }
        }
    }
}

@Serializable
object Onboard

@Serializable
object Home

@Serializable
object Entry