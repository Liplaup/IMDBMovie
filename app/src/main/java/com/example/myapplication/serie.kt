package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun SerieDetailScreen(serieId: Int, viewModel: MainViewModel, navController: NavController) {
    // Observer des données depuis le ViewModel (la série et les acteurs)
    val serie by viewModel.serie.observeAsState()
    val actors by viewModel.actors.collectAsState()

    // Variables d'état pour gérer l'affichage de l'indicateur de chargement et les erreurs
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Effet de lancement pour récupérer les détails de la série et les acteurs au démarrage
    LaunchedEffect(serieId) {
        try {
            // Appel au ViewModel pour récupérer les détails de la série et les acteurs
            viewModel.getSerieById(serieId, apiKey = "d56137a7d2c77892dd70729b2a4ee56b")
            viewModel.getActorsBySerieId(serieId, apiKey = "d56137a7d2c77892dd70729b2a4ee56b")
            // Mise à jour de l'état de chargement
            isLoading.value = false
        } catch (e: Exception) {
            // Si une erreur se produit, afficher le message d'erreur
            errorMessage.value = e.message
            isLoading.value = false
        }
    }

    // Affichage d'un indicateur de chargement pendant que les données sont récupérées
    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // Indicateur de chargement circulaire
        }
    } else {
        // Si une erreur est survenue, afficher le message d'erreur
        errorMessage.value?.let {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: $it", style = MaterialTheme.typography.bodyMedium) // Message d'erreur
            }
        } ?: run {
            // Si la série est récupérée avec succès, afficher ses détails
            serie?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()) // Permet de défiler verticalement
                        .padding(16.dp)
                ) {
                    // Afficher le nom de la série en grand
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    // Afficher l'image de fond de la série
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${it.backdrop_path}",
                        contentDescription = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Hauteur de l'image de fond
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Espacement entre l'image et les autres éléments
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Afficher l'image du poster de la série
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/${it.poster_path}",
                            contentDescription = it.name,
                            modifier = Modifier
                                .weight(1f)
                                .height(300.dp) // Hauteur du poster
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Espacement horizontal
                        // Afficher les informations supplémentaires sur la série
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "First Air Date: ${it.first_air_date}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les lignes
                            Text(
                                text = "Genres: ${it.genres.joinToString { genre -> genre.name }}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp)) // Espacement entre la section des infos et la synopsis
                    // Afficher le titre "Synopsis"
                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Afficher la description de la série
                    Text(
                        text = it.overview,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Espacement avant les acteurs
                    // Afficher le titre "Headliners" pour les acteurs principaux
                    Text(
                        text = "Headliners",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Liste des acteurs principaux dans un LazyRow (carrousel horizontal)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(actors) { actor ->
                            // Pour chaque acteur, afficher son image et son nom
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(128.dp)
                            ) {
                                // Afficher l'image de profil de l'acteur
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500/${actor.profile_path}",
                                    contentDescription = actor.name,
                                    modifier = Modifier.size(128.dp) // Taille de l'image de profil
                                )
                                Spacer(modifier = Modifier.height(4.dp)) // Espacement entre l'image et le nom
                                // Afficher le nom de l'acteur
                                Text(
                                    text = actor.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                // Afficher le personnage joué par l'acteur
                                Text(
                                    text = "as ${actor.character}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(64.dp)) // Espacement avant la fin de la page
                }
            } ?: run {
                // Si aucune donnée de série n'est trouvée, afficher un message d'erreur
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Serie details not found.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
