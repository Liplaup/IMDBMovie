package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Spacer as Spacer1
import androidx.compose.material3.Text as Text1


@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MainViewModel, navController: NavController) {
    // Observer les données du film et des acteurs depuis le ViewModel
    val movie by viewModel.movie.observeAsState()
    val actors by viewModel.actors.collectAsState()

    // Variable pour afficher un indicateur de chargement pendant le chargement des données
    val isLoading = remember { mutableStateOf(true) }

    // Lorsque l'ID du film change, on effectue les appels pour récupérer les informations sur le film et les acteurs
    LaunchedEffect(movieId) {
        viewModel.getMovieById(movieId, apiKey = "d56137a7d2c77892dd70729b2a4ee56b")
        viewModel.getActorsByMovieId(movieId, apiKey = "d56137a7d2c77892dd70729b2a4ee56b")
        isLoading.value = false
    }

    // Afficher un indicateur de chargement tant que les données ne sont pas prêtes
    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // Indicateur circulaire de chargement
        }
    } else {
        movie?.let {
            // Afficher les détails du film une fois les données récupérées
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Permet de faire défiler le contenu verticalement
                    .background(colorScheme.background) // Fond d'écran
                    .padding(16.dp) // Espacement autour du contenu
                    .navigationBarsPadding() // Ajouter un padding pour la barre de navigation
            ) {
                // Titre du film
                Text1(
                    text = it.title,
                    style = typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                // Image de fond du film
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${it.backdrop_path}",
                    contentDescription = it.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer1(modifier = Modifier.height(16.dp)) // Espacement après l'image de fond
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Affiche l'image de l'affiche du film
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${it.poster_path}",
                        contentDescription = it.title,
                        modifier = Modifier
                            .weight(1f)
                            .height(300.dp)
                    )
                    Spacer1(modifier = Modifier.width(16.dp)) // Espacement entre l'affiche et les détails
                    // Détails du film à côté de l'affiche
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text1(
                            text = "Release Date: ${it.release_date}",
                            style = typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                        Spacer1(modifier = Modifier.height(8.dp))
                        Text1(
                            text = "Genres: ${it.genres.joinToString { genre -> genre.name }}",
                            style = typography.bodyLarge,
                            color = colorScheme.onBackground
                        )
                    }
                }
                Spacer1(modifier = Modifier.height(16.dp)) // Espacement entre les sections
                // Section Synopsis
                Text1(
                    text = "Synopsis",
                    style = typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
                Spacer1(modifier = Modifier.height(8.dp))
                Text1(
                    text = it.overview,
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground
                )
                Spacer1(modifier = Modifier.height(16.dp))
                // Section "Headliners" (Acteurs principaux)
                Text1(
                    text = "Headliners",
                    style = typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
                Spacer1(modifier = Modifier.height(8.dp))
                // Liste horizontale des acteurs principaux
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .navigationBarsPadding()
                ) {
                    items(actors) { actor ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(128.dp)
                        ) {
                            // Image de profil de l'acteur
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500/${actor.profile_path}",
                                contentDescription = actor.name,
                                modifier = Modifier
                                    .size(128.dp)
                            )
                            Spacer1(modifier = Modifier.height(4.dp))
                            // Nom de l'acteur
                            Text1(
                                text = actor.name,
                                style = typography.bodyLarge,
                                color = colorScheme.onBackground
                            )
                            // Rôle de l'acteur dans le film
                            Text1(
                                text = "as ${actor.character}",
                                style = typography.bodyMedium,
                                color = colorScheme.onBackground
                            )
                        }
                    }
                }
                Spacer1(modifier = Modifier.height(64.dp)) // Espacement final
            }
        } ?: run {
            // Afficher un message si les détails du film ne sont pas trouvés
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text1("Movie details not found.", style = typography.bodyMedium)
            }
        }
    }
}
