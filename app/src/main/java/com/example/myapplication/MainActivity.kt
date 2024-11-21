package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                AppNavHost(navController, viewModel)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, viewModel: MainViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            if (currentDestination?.route != "profile") {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_films), contentDescription = "Films") },
                        label = { Text("Films") },
                        selected = currentDestination?.route == "films",
                        onClick = { navController.navigate("films") }
                    )
                    NavigationBarItem(
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_series), contentDescription = "Séries") },
                        label = { Text("Séries") },
                        selected = currentDestination?.route == "series",
                        onClick = { navController.navigate("series") }
                    )
                    NavigationBarItem(
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_action_person), contentDescription = "Acteurs") },
                        label = { Text("Acteurs") },
                        selected = currentDestination?.route == "actors",
                        onClick = { navController.navigate("actors") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "profile",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("profile") {
                ProfileView(navController)
            }
            composable("films") {
                FilmsScreen(viewModel = viewModel, navController = navController)
            }
            composable("series") {
                // Implement your SeriesScreen here
            }
            composable("actors") {
                // Implement your ActorsScreen here
            }
        }
    }
}
