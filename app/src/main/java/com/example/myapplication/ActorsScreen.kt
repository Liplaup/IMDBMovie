package com.example.myapplication

// Importation des bibliothèques nécessaires pour l'interface utilisateur
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

// Composable pour afficher une carte avec les détails d'un acteur
@Composable
fun ActorCard(tmdbActor: TmdbActor, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp) // Ajoute de l'espace autour de la carte
            .clickable {}, // Comportement au clic sur la carte (vide pour le moment)
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Élévation de la carte
    ) {
        // Colonne qui contient l'image de l'acteur et son nom
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            // Affichage de l'image de l'acteur (utilisation d'une image distante)
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${tmdbActor.profile_path}",
                contentDescription = tmdbActor.name,
                modifier = Modifier
                    .fillMaxWidth() // L'image prend toute la largeur disponible
                    .height(200.dp) // Hauteur fixe pour l'image
            )
            // Affichage du nom de l'acteur
            Text(
                text = tmdbActor.name,
                fontWeight = FontWeight.Bold, // Met en gras le nom
                textAlign = TextAlign.Center, // Centrer le texte
                modifier = Modifier.padding(top = 8.dp) // Marge au-dessus du texte
            )
        }
    }
}

// Composable pour afficher une grille d'acteurs
@Composable
fun ActorsGrid(tmdbActors: List<TmdbActor>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp), // Les cellules de la grille s'adaptent à 150dp
        modifier = Modifier
            .fillMaxSize() // Remplir toute la taille disponible
            .padding(8.dp) // Ajouter des marges autour de la grille
    ) {
        items(tmdbActors) { actor -> // Pour chaque acteur dans la liste tmdbActors
            ActorCard(
                tmdbActor = actor, // Affichage de la carte de l'acteur
                navController = navController
            )
        }
    }
}

// Composable pour l'écran principal affichant les acteurs
@OptIn(ExperimentalMaterial3Api::class) // Annotation pour utiliser des API expérimentales
@Composable
fun ActorsScreen(viewModel: MainViewModel, navController: NavController) {
    // Variables d'état pour la gestion de la recherche
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) } // Texte de la recherche
    var isSearching by remember { mutableStateOf(false) } // Indicateur si l'utilisateur recherche
    val actors by viewModel.actors.collectAsState() // Liste des acteurs récupérée du ViewModel
    val isLoading = remember { mutableStateOf(true) } // Indicateur de chargement
    val configuration = LocalConfiguration.current // Récupère la configuration de l'appareil (orientation, etc.)

    // Lancement d'une action dès que l'écran est affiché
    LaunchedEffect(Unit) {
        if (actors.isEmpty()) {
            // Si la liste des acteurs est vide, on récupère les acteurs depuis le ViewModel
            viewModel.getActors("d56137a7d2c77892dd70729b2a4ee56b")
        }
        isLoading.value = false // On indique que le chargement est terminé
    }

    // Définir un padding inférieur en fonction de l'orientation de l'écran (paysage ou portrait)
    val bottomPadding = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.dp else 100.dp

    // Colonne principale contenant tous les éléments de l'écran
    Column(
        modifier = Modifier
            .fillMaxSize() // Remplir toute la taille disponible
            .padding(bottom = bottomPadding) // Ajouter un padding en bas
    ) {
        // AppBar en haut de l'écran avec un champ de recherche et un bouton pour activer la recherche
        TopAppBar(
            title = {
                // Si on est en mode recherche, afficher un champ de texte
                if (isSearching) {
                    OutlinedTextField(
                        value = searchQuery, // Le texte actuel dans le champ
                        onValueChange = { query -> // Action au changement du texte
                            searchQuery = query
                            viewModel.searchActors("d56137a7d2c77892dd70729b2a4ee56b", query.text)
                        },
                        label = { Text("Rechercher un acteur") }, // Étiquette du champ de texte
                        singleLine = true, // Champ de texte sur une seule ligne
                        modifier = Modifier
                            .fillMaxWidth() // Le champ prend toute la largeur disponible
                            .padding(16.dp) // Marge autour du champ
                    )
                } else {
                    // Sinon, afficher juste le titre "Actors"
                    Text("Actors")
                }
            },
            actions = {
                // Icône de recherche pour activer/désactiver le mode recherche
                IconButton(onClick = {
                    isSearching = !isSearching
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        // Afficher un indicateur de chargement pendant la récupération des données
        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(), // Remplir toute la taille disponible
                contentAlignment = Alignment.Center // Centrer l'indicateur de chargement
            ) {
                CircularProgressIndicator() // Affichage de l'indicateur de chargement
            }
        } else if (actors.isEmpty()) {
            // Si la liste des acteurs est vide, afficher un message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No actors found.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            // Sinon, afficher la grille des acteurs
            ActorsGrid(
                tmdbActors = actors,
                navController = navController
            )
        }
    }
}
