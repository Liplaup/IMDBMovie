package com.example.myapplication

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material3.OutlinedTextField

// Composant qui affiche une carte pour chaque série
@Composable
fun SeriesCard(tmdbSeries: TmdbSeries, navController: NavController) {
    // Carte cliquable qui redirige vers l'écran de détail de la série
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("serieDetail/${tmdbSeries.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        // Contenu de la carte : une image, un titre et la première date de diffusion
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            // Affiche l'image de la série (poster)
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${tmdbSeries.poster_path}",
                contentDescription = tmdbSeries.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            // Titre de la série
            Text(
                text = tmdbSeries.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            // Date de première diffusion
            Text(
                text = tmdbSeries.first_air_date,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// Composant qui affiche la grille de séries
@Composable
fun SeriesGrid(tmdbSeries: List<TmdbSeries>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Pour chaque série, affiche une carte
        items(tmdbSeries) { series ->
            SeriesCard(
                tmdbSeries = series,
                navController = navController
            )
        }
    }
}

// Écran principal des séries
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesScreen(viewModel: MainViewModel, navController: NavController) {
    // Variables pour gérer l'état de la recherche
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var isSearching by remember { mutableStateOf(false) }
    // Récupère les séries depuis le ViewModel
    val series by viewModel.series.collectAsState()
    val isLoading = remember { mutableStateOf(true) }
    val configuration = LocalConfiguration.current

    // Appel de la fonction pour récupérer les séries lorsque l'écran est lancé
    LaunchedEffect(Unit) {
        if (series.isEmpty()) {
            viewModel.getSeries("d56137a7d2c77892dd70729b2a4ee56b")
        }
        isLoading.value = false
    }

    // Calcul du padding en fonction de l'orientation de l'écran (paysage ou portrait)
    val bottomPadding = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.dp else 100.dp

    // Structure de l'écran
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
    ) {
        // Barre d'application avec le champ de recherche
        TopAppBar(
            title = {
                if (isSearching) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            viewModel.searchSeries("d56137a7d2c77892dd70729b2a4ee56b", query.text)
                        },
                        label = { Text("Rechercher une série") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                } else {
                    Text("Series")
                }
            },
            actions = {
                // Bouton pour activer/désactiver la recherche
                IconButton(onClick = {
                    isSearching = !isSearching
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        // Affichage du loader lorsque les séries sont en cours de chargement
        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (series.isEmpty()) {
            // Si aucune série n'est trouvée, affiche un message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No series found.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            // Affiche la grille des séries
            SeriesGrid(
                tmdbSeries = series,
                navController = navController
            )
        }
    }
}
