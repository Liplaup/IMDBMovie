package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val moshi = Moshi.Builder().build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(Api::class.java)

    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())

    fun getMovies(apikey: String) {
        viewModelScope.launch {
            try {
                movies.value = api.lastmovies(apikey).results
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
}