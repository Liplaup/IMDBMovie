package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

// Classe de destination pour le profil
@Serializable
class Profildestination

// Classe de destination pour les films
@Serializable
class Filmsdestination

// Classe de destination pour les séries
@Serializable
class Seriesdestination

// Classe de destination pour les acteurs
@Serializable
class Acteursdestination

// Activité principale
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Active l'affichage de contenu jusqu'au bord de l'écran
        setContent {
            val viewModel: MainViewModel by viewModels() // Initialisation du ViewModel
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass // Récupération des informations sur la taille de la fenêtre

            MyApplicationTheme {
                val navController = rememberNavController() // Contrôleur de navigation
                val navBackStackEntry by navController.currentBackStackEntryAsState() // Récupère l'entrée du backstack actuel
                val currentDestination = navBackStackEntry?.destination // Destination actuelle

                BoxWithConstraints {
                    // Si l'écran est en mode paysage, afficher une barre de navigation horizontale
                    if (maxWidth > maxHeight) {
                        Row {
                            // Si la destination actuelle n'est pas celle du profil, afficher la barre de navigation latérale
                            if (currentDestination?.route != "profildestination") {
                                Navbar(navController, Modifier.fillMaxHeight().width(80.dp), isVertical = false)
                            }
                            // Conteneur de la navigation et affichage des destinations
                            NavHostContainer(navController, viewModel, Modifier.fillMaxHeight().weight(1f), windowSizeClass)
                        }
                    } else {
                        // Sinon, afficher la barre de navigation en bas
                        Scaffold(
                            bottomBar = {
                                if (currentDestination?.route != "profildestination") {
                                    Navbar(navController, Modifier.fillMaxWidth(), isVertical = true)
                                }
                            }
                        ) {
                            NavHostContainer(navController, viewModel, Modifier.fillMaxSize(), windowSizeClass)
                        }
                    }
                }
            }
        }
    }
}

// Fonction composable pour la barre de navigation
@Composable
fun Navbar(navController: NavHostController, modifier: Modifier, isVertical: Boolean) {
    // Si la navigation est verticale
    if (isVertical) {
        NavigationBar(
            containerColor = Color.White,
            modifier = modifier
        ) {
            // Item de la barre de navigation pour les films
            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.ic_films), contentDescription = "Films") },
                label = { Text("Films") },
                selected = navController.currentDestination?.route == "filmsdestination",
                onClick = { navController.navigate("filmsdestination") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
            // Item de la barre de navigation pour les séries
            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.ic_series), contentDescription = "Series") },
                label = { Text("Series") },
                selected = navController.currentDestination?.route == "seriesdestination",
                onClick = { navController.navigate("seriesdestination") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
            // Item de la barre de navigation pour les acteurs
            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.ic_action_person), contentDescription = "Acteurs") },
                label = { Text("Acteurs") },
                selected = navController.currentDestination?.route == "acteursdestination",
                onClick = { navController.navigate("acteursdestination") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    } else {
        // Si la navigation est horizontale, utiliser une barre latérale
        NavigationRail(
            containerColor = Color.White,
            modifier = modifier.fillMaxHeight()
        ) {
            // Item de la barre latérale pour les films
            NavigationRailItem(
                icon = { Icon(painterResource(id = R.drawable.ic_films), contentDescription = "Films") },
                label = { Text("Films") },
                selected = navController.currentDestination?.route == "filmsdestination",
                onClick = { navController.navigate("filmsdestination") },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
            // Item de la barre latérale pour les séries
            NavigationRailItem(
                icon = { Icon(painterResource(id = R.drawable.ic_series), contentDescription = "Series") },
                label = { Text("Series") },
                selected = navController.currentDestination?.route == "seriesdestination",
                onClick = { navController.navigate("seriesdestination") },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
            // Item de la barre latérale pour les acteurs
            NavigationRailItem(
                icon = { Icon(painterResource(id = R.drawable.ic_action_person), contentDescription = "Acteurs") },
                label = { Text("Acteurs") },
                selected = navController.currentDestination?.route == "acteursdestination",
                onClick = { navController.navigate("acteursdestination") },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

// Fonction composable pour le conteneur de navigation
@Composable
fun NavHostContainer(navController: NavHostController, viewModel: MainViewModel, modifier: Modifier, windowSizeClass: WindowSizeClass) {
    NavHost(navController = navController, startDestination = "profildestination", modifier = modifier) {
        // Définition des destinations dans le NavHost
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
