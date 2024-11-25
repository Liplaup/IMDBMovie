package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

@Serializable
class Profildestination

@Serializable
class Filmsdestination

@Serializable
class Seriesdestination

@Serializable
class Acteursdestination

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel by viewModels()
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

            MyApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    bottomBar = {
                        if (currentDestination?.route != "profildestination") {
                            Navbar(navController)
                        }
                    }
                ) {
                    NavHost(navController = navController, startDestination = "profildestination") {
                        composable("profildestination") {
                            ProfileView(windowSizeClass) { navController.navigate("filmsdestination") }
                        }
                        composable("filmsdestination") {
                            FilmsScreen(viewModel, navController)
                        }
                        composable("seriesdestination") {
                            SeriesScreen(viewModel, navController)
                        }
                        composable("acteursdestination") {
                            ActorsScreen(viewModel, navController)
                        }
                        composable("movieDetail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            MovieDetailScreen(movieId?.toInt() ?: 0, viewModel, navController)
                        }
                        composable("serieDetail/{seriesId}") { backStackEntry ->
                            val seriesId = backStackEntry.arguments?.getString("seriesId")
                            SerieDetailScreen(seriesId?.toInt() ?: 0, viewModel, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navbar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.Gray
    ) {
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_films), contentDescription = "Films") },
            label = { Text("Films") },
            selected = navController.currentDestination?.route == "filmsdestination",
            onClick = { navController.navigate("filmsdestination") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_series), contentDescription = "Series") },
            label = { Text("Series") },
            selected = navController.currentDestination?.route == "seriesdestination",
            onClick = { navController.navigate("seriesdestination") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_action_person), contentDescription = "Acteurs") },
            label = { Text("Acteurs") },
            selected = navController.currentDestination?.route == "acteursdestination",
            onClick = { navController.navigate("acteursdestination") }
        )
    }
}