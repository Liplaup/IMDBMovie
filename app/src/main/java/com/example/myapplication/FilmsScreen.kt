package com.example.myapplication

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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

// Composant pour afficher une carte d'acteur
@Composable
fun FilmCard(tmdbMovie: TmdbMovie, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)  // Marge autour de la carte
            .fillMaxWidth()  // Largeur maximale de la carte
            .wrapContentHeight()  // Hauteur ajustée au contenu
            .clickable { navController.navigate("movieDetail/${tmdbMovie.id}") },  // Navigation au détail du film lors du clic
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)  // Élément d'élévation de la carte
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,  // Alignement centré
            modifier = Modifier.padding(8.dp)  // Marge intérieure de la colonne
        ) {
            // Affichage de l'image du film
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${tmdbMovie.poster_path}",  // URL de l'image
                contentDescription = tmdbMovie.title,  // Description de l'image
                modifier = Modifier
                    .fillMaxWidth()  // Largeur maximale de l'image
                    .height(200.dp)  // Hauteur fixée de l'image
            )
            // Affichage du titre du film
            Text(
                text = tmdbMovie.title,
                fontWeight = FontWeight.Bold,  // Texte en gras
                textAlign = TextAlign.Center,  // Texte centré
                modifier = Modifier
                    .fillMaxWidth()  // Largeur maximale du texte
                    .padding(top = 8.dp)  // Marge en haut du texte
            )
            // Affichage de la date de sortie du film
            Text(
                text = tmdbMovie.release_date,
                style = MaterialTheme.typography.bodySmall,  // Style du texte
                textAlign = TextAlign.Center,  // Texte centré
                modifier = Modifier
                    .fillMaxWidth()  // Largeur maximale du texte
                    .padding(top = 4.dp)  // Marge en haut du texte
            )
        }
    }
}

// Composant pour afficher une grille de films
@Composable
fun FilmsGrid(tmdbMovies: List<TmdbMovie>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),  // Grille adaptative avec taille minimale de 150dp par cellule
        modifier = Modifier
            .fillMaxSize()  // Remplir toute la taille disponible
            .padding(8.dp)  // Marge autour de la grille
    ) {
        // Affichage des films dans la grille
        items(tmdbMovies) { movie ->
            FilmCard(
                tmdbMovie = movie,
                navController = navController
            )
        }
    }
}

// Composant principal de l'écran des films
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsScreen(viewModel: MainViewModel, navController: NavController) {
    // Variables d'état pour la recherche et les films
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }  // Valeur de la recherche
    var isSearching by remember { mutableStateOf(false) }  // État de la recherche (activée ou non)
    val movies by viewModel.movies.collectAsState()  // Liste des films du ViewModel
    val isLoading = remember { mutableStateOf(true) }  // Indicateur de chargement
    val configuration = LocalConfiguration.current  // Récupère la configuration de l'appareil (portrait/paysage)

    // Effet de lancement pour charger les films si la liste est vide
    LaunchedEffect(Unit) {
        if (movies.isEmpty()) {
            viewModel.getMovies("d56137a7d2c77892dd70729b2a4ee56b")  // Charge les films à partir de l'API
        }
        isLoading.value = false  // Terminer le chargement
    }

    // Ajuste le padding en fonction de l'orientation de l'appareil (paysage ou portrait)
    val bottomPadding = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.dp else 100.dp

    // Colonne contenant tous les éléments de l'écran
    Column(
        modifier = Modifier
            .fillMaxSize()  // Remplir toute la taille disponible
            .padding(bottom = bottomPadding)  // Padding en bas
    ) {
        // Barre d'applications en haut avec possibilité de recherche
        TopAppBar(
            title = {
                // Affichage du champ de recherche si l'utilisateur est en mode recherche
                if (isSearching) {
                    OutlinedTextField(
                        value = searchQuery,  // Valeur du champ de recherche
                        onValueChange = { query ->  // Mise à jour de la valeur de recherche
                            searchQuery = query
                            viewModel.searchMovies("d56137a7d2c77892dd70729b2a4ee56b", query.text)  // Recherche des films
                        },
                        label = { Text("Rechercher un film") },  // Étiquette du champ de recherche
                        singleLine = true,  // Champ de recherche sur une seule ligne
                        modifier = Modifier
                            .fillMaxWidth()  // Largeur maximale
                            .padding(16.dp)  // Marge autour du champ de texte
                    )
                } else {
                    Text("Films")  // Titre de la page lorsque la recherche est désactivée
                }
            },
            actions = {
                // Bouton de recherche pour activer/désactiver le mode recherche
                IconButton(onClick = {
                    isSearching = !isSearching  // Basculer l'état de recherche
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")  // Icône de recherche
                }
            }
        )

        // Affichage du chargement si nécessaire
        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),  // Remplir la taille disponible
                contentAlignment = Alignment.Center  // Centrer le contenu
            ) {
                CircularProgressIndicator()  // Indicateur de chargement circulaire
            }
        } else if (movies.isEmpty()) {
            // Message lorsqu'aucun film n'est trouvé
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucun film trouvé.", style = MaterialTheme.typography.bodyMedium)  // Texte d'alerte
            }
        } else {
            // Affichage des films sous forme de grille
            FilmsGrid(
                tmdbMovies = movies,
                navController = navController
            )
        }
    }
}
