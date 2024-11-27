package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interface définissant les appels à l'API de TMDb (The Movie Database)
interface Api {

    // Récupère les films actuellement diffusés
    @GET("movie/now_playing")
    suspend fun lastmovies(@Query("api_key") apiKey: String): TmdbMoviesResult

    // Recherche des films en fonction du nom de la série ou du film
    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbMoviesResult

    // Récupère les séries télévisées populaires
    @GET("tv/popular")
    suspend fun popularSeries(@Query("api_key") apiKey: String): TmdbSeriesResult

    // Recherche des séries en fonction du nom
    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbSeriesResult

    // Récupère les acteurs populaires
    @GET("person/popular")
    suspend fun popularActors(@Query("api_key") apiKey: String): TmdbActorsResult

    // Recherche des acteurs en fonction de leur nom
    @GET("search/person")
    suspend fun searchActors(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbActorsResult

    // Récupère les détails d'un film en fonction de son identifiant
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int, // Le paramètre movie_id sera inséré dans l'URL
        @Query("api_key") apiKey: String // La clé API pour l'accès aux données
    ): TmdbMovie

    // Récupère les détails d'une série télévisée en fonction de son identifiant
    @GET("tv/{serie_id}")
    suspend fun getSerieDetails(
        @Path("serie_id") serieId: Int, // Le paramètre serie_id sera inséré dans l'URL
        @Query("api_key") apiKey: String // La clé API pour l'accès aux données
    ): TmdbSeries

    // Récupère les acteurs d'un film en fonction de l'identifiant du film
    @GET("movie/{movie_id}/credits")
    suspend fun getActorsByMovieId(
        @Path("movie_id") movieId: Int, // L'identifiant du film
        @Query("api_key") apiKey: String // La clé API pour l'accès aux données
    ): ListActor

    // Récupère les acteurs d'une série télévisée en fonction de l'identifiant de la série
    @GET("tv/{tv_id}/credits")
    suspend fun getActorsBySerieId(
        @Path("tv_id") tvId: Int, // L'identifiant de la série
        @Query("api_key") apiKey: String // La clé API pour l'accès aux données
    ): ListActor

}
