package com.example.myapplication

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun FilmsScreen(viewModel: MainViewModel, navController: NavController) {
    val movies by viewModel.movies.collectAsState()
    val isLoading = remember { mutableStateOf(true) } // État de chargement

    LaunchedEffect(Unit) {
        if (movies.isEmpty()) {
            viewModel.getMovies("d56137a7d2c77892dd70729b2a4ee56b")
        }
        isLoading.value = false
    }

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (movies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Aucun film trouvé.", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        FilmsGrid(
            tmdbMovies = movies,
            navController = navController
        )
    }
}

@Composable
fun FilmsGrid(tmdbMovies: List<TmdbMovie>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        items(tmdbMovies) { movie ->
            FilmCard(
                tmdbMovie = movie,
                navController = navController
            )
        }
    }
}

@Composable
fun FilmCard(tmdbMovie: TmdbMovie, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate(FilmDetailDest(tmdbMovie.id)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${tmdbMovie.poster_path}",
                contentDescription = tmdbMovie.title,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Text(
                text = tmdbMovie.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = tmdbMovie.release_date,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
