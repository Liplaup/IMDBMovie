package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    // Création d'une instance Retrofit pour l'accès à l'API
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")  // URL de base de l'API
        .addConverterFactory(GsonConverterFactory.create())  // Utilisation de Gson pour la conversion JSON
        .build()

    // Création de l'interface API
    private val api = retrofit.create(Api::class.java)

    // Flux d'état pour stocker les listes de films, séries et acteurs
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val series = MutableStateFlow<List<TmdbSeries>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())

    // LiveData pour les films et séries sélectionnés
    private val _movie = MutableLiveData<TmdbMovie>()
    val movie: LiveData<TmdbMovie> get() = _movie

    private val _serie = MutableLiveData<TmdbSeries>()
    val serie: LiveData<TmdbSeries> get() = _serie

    // Fonction pour récupérer les films populaires
    fun getMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir les derniers films
                movies.value = api.lastmovies(apiKey).results
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des films", e)
            }
        }
    }

    // Fonction pour rechercher des films en fonction d'une requête
    fun searchMovies(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour rechercher des films
                movies.value = api.searchMovies(apiKey, query).results
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la recherche de films", e)
            }
        }
    }

    // Fonction pour récupérer les séries populaires
    fun getSeries(apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir les séries populaires
                series.value = api.popularSeries(apiKey).results
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des séries", e)
            }
        }
    }

    // Fonction pour rechercher des séries en fonction d'une requête
    fun searchSeries(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour rechercher des séries
                series.value = api.searchSeries(apiKey, query).results
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la recherche de séries", e)
            }
        }
    }

    // Fonction pour récupérer les acteurs populaires
    fun getActors(apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir les acteurs populaires
                actors.value = api.popularActors(apiKey).results
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des acteurs", e)
            }
        }
    }

    // Fonction pour rechercher des acteurs en fonction d'une requête
    fun searchActors(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour rechercher des acteurs
                actors.value = api.searchActors(apiKey, query).results
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la recherche d'acteurs", e)
            }
        }
    }

    // Fonction pour récupérer les détails d'un film par son ID
    fun getMovieById(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir les détails du film
                val movieDetails = api.getMovieDetails(movieId, apiKey)
                // Mise à jour de LiveData avec les détails du film
                _movie.postValue(movieDetails)
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des détails du film", e)
            }
        }
    }

    // Fonction pour récupérer les détails d'une série par son ID
    fun getSerieById(serieId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir les détails de la série
                val serieDetails = api.getSerieDetails(serieId, apiKey)
                // Mise à jour de LiveData avec les détails de la série
                _serie.postValue(serieDetails)
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des détails de la série", e)
            }
        }
    }

    // Fonction pour récupérer les acteurs associés à un film par son ID
    fun getActorsByMovieId(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir la liste des acteurs du film
                val actorList = api.getActorsByMovieId(movieId, apiKey).cast
                // Mise à jour de l'état des acteurs
                actors.value = actorList
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des acteurs par ID de film", e)
            }
        }
    }

    // Fonction pour récupérer les acteurs associés à une série par son ID
    fun getActorsBySerieId(serieId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                // Appel à l'API pour obtenir la liste des acteurs de la série
                val actorList = api.getActorsBySerieId(serieId, apiKey).cast
                // Mise à jour de l'état des acteurs
                actors.value = actorList
            } catch (e: Exception) {
                // Gestion des erreurs
                Log.e("MainViewModel", "Erreur lors de la récupération des acteurs par ID de série", e)
            }
        }
    }
}
