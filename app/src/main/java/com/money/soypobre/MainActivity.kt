package com.money.soypobre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.money.soypobre.home.presentation.HomeScreen
import com.money.soypobre.onboard.presentation.OnboardScreen
import com.money.soypobre.ui.theme.SoyPobreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoyPobreTheme {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    startDestination = Onboard
                ) {
                    composable<Onboard> {
                        OnboardScreen()
                    }
                    composable<Home> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Serializable
object Home

@Serializable
object Onboard