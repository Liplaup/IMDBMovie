package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") apikey: String): TmdbMoviesResult
}