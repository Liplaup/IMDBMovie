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
    val serie by viewModel.serie.observeAsState()
    val actors by viewModel.actors.collectAsState()
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(serieId) {
        viewModel.getSerieById(serieId, apiKey = "d56137a7d2c77892dd70729b2a4ee56b")
        viewModel.getActorsBySerieId(serieId, apiKey = "d56137a7d2c77892dd70729b2a4ee56b")
        isLoading.value = false
    }

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        serie?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${it.backdrop_path}",
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${it.poster_path}",
                        contentDescription = it.name,
                        modifier = Modifier
                            .weight(1f)
                            .height(300.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "First Air Date: ${it.first_air_date}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Genres: ${it.genres.joinToString { genre -> genre.name }}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = it.overview,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Headliners",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    items(actors) { actor ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(128.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500/${actor.profile_path}",
                                contentDescription = actor.name,
                                modifier = Modifier.size(128.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = actor.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "as ${actor.character}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(64.dp))
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Serie details not found.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}