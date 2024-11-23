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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesScreen(viewModel: MainViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var isSearching by remember { mutableStateOf(false) }
    val series by viewModel.series.collectAsState()
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (series.isEmpty()) {
            viewModel.getSeries("d56137a7d2c77892dd70729b2a4ee56b")
        }
        isLoading.value = false
    }

    Column {
        TopAppBar(
            title = {
                if (isSearching) {
                    TextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            viewModel.searchSeries("d56137a7d2c77892dd70729b2a4ee56b", searchQuery.text)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search...") }
                    )
                } else {
                    Text("Series")
                }
            },
            actions = {
                IconButton(onClick = {
                    isSearching = !isSearching
                    if (!isSearching) {
                        viewModel.getSeries("d56137a7d2c77892dd70729b2a4ee56b")
                    }
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        if (isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (series.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No series found.", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            SeriesGrid(
                tmdbSeries = series,
                navController = navController
            )
        }
    }
}

@Composable
fun SeriesGrid(tmdbSeries: List<TmdbSeries>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(tmdbSeries) { series ->
            SeriesCard(
                tmdbSeries = series,
                navController = navController
            )
        }
    }
}

@Composable
fun SeriesCard(tmdbSeries: TmdbSeries, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* No navigation for now */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${tmdbSeries.poster_path}",
                contentDescription = tmdbSeries.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = tmdbSeries.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = tmdbSeries.first_air_date,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}