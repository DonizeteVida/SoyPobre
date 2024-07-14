package com.money.soypobre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.money.soypobre.ui.theme.SoyPobreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoyPobreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController,
                        modifier = Modifier.padding(padding),
                        startDestination = Login
                    ) {
                        composable<Login> {
                            Column {
                                Text("Hello User")
                                Button(onClick = {
                                    navController.navigate(Home(id = 1))
                                }) {
                                    Text(text = "Click here to navigate")
                                }
                            }
                        }
                        composable<Home> { backStackEntry ->
                            val home: Home = backStackEntry.toRoute()
                            val viewModel: MainViewModel = hiltViewModel()
                            print(viewModel)
                            Column {
                                Text(text = "Hello user: " + home.id)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
class Home(
    val id: Int
)

@Serializable
object Login

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoyPobreTheme {
        Greeting("Android")
    }
}