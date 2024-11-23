@file:Suppress("PLUGIN_IS_NOT_ENABLED")

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
sealed class Destination(val route: String) {
    @Serializable object Profil : Destination("profil")
    @Serializable object Films : Destination("films")
    @Serializable object Series : Destination("series")
    @Serializable object Acteurs : Destination("acteurs")
}

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel by viewModels()

            MyApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(bottomBar = { if (currentRoute != Destination.Profil.route) Navbar(navController) }) {
                    NavHost(navController = navController, startDestination = Destination.Profil.route) {
                        composable(Destination.Profil.route) {
                            ProfileView(navController)
                        }
                        composable(Destination.Films.route) {
                            FilmsScreen(viewModel = viewModel, navController = navController)
                        }
                        composable(Destination.Series.route) {
                            SeriesScreen(viewModel = viewModel, navController = navController)
                        }
                        composable(Destination.Acteurs.route) {
                            ActorsScreen(viewModel = viewModel, navController = navController)
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
            selected = navController.currentDestination?.route == Destination.Films.route,
            onClick = { navController.navigate(Destination.Films.route) }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_series), contentDescription = "Series") },
            label = { Text("Series") },
            selected = navController.currentDestination?.route == Destination.Series.route,
            onClick = { navController.navigate(Destination.Series.route) }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_action_person), contentDescription = "Acteurs") },
            label = { Text("Acteurs") },
            selected = navController.currentDestination?.route == Destination.Acteurs.route,
            onClick = { navController.navigate(Destination.Acteurs.route) }
        )
    }
}