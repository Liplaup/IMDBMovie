package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/now_playing")
    suspend fun lastmovies(@Query("api_key") apiKey: String): TmdbMoviesResult

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbMoviesResult

    @GET("tv/popular")
        suspend fun popularSeries(@Query("api_key") apiKey: String): TmdbSeriesResult

    @GET("search/tv")
        suspend fun searchSeries(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbSeriesResult

    @GET("person/popular")
    suspend fun popularActors(@Query("api_key") apiKey: String): TmdbActorsResult

    @GET("search/person")
    suspend fun searchActors(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbActorsResult
}