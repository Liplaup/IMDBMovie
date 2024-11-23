package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val series = MutableStateFlow<List<TmdbSeries>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf()) // Corrected variable

    fun getMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                movies.value = api.lastmovies(apiKey).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching movies", e)
            }
        }
    }

    fun searchMovies(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                movies.value = api.searchMovies(apiKey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error searching movies", e)
            }
        }
    }

    fun getSeries(apiKey: String) {
        viewModelScope.launch {
            try {
                series.value = api.popularSeries(apiKey).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching series", e)
            }
        }
    }

    fun searchSeries(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                series.value = api.searchSeries(apiKey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error searching series", e)
            }
        }
    }

    fun getActors(apiKey: String) {
        viewModelScope.launch {
            try {
                actors.value = api.popularActors(apiKey).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching actors", e)
            }
        }
    }

    fun searchActors(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                actors.value = api.searchActors(apiKey, query).results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error searching actors", e)
            }
        }
    }
}