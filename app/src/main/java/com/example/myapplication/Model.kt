package com.example.myapplication


data class TmdbMoviesResult(
    val page: Int,
    val results: List<TmdbMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class TmdbMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class TmdbSeriesResult(
    val page: Int,
    val results: List<TmdbSeries>,
    val total_pages: Int,
    val total_results: Int
)

data class TmdbSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Double,
    val vote_count: Int,
    val first_air_date: String, // Correct property name
    val original_name: String,
    val media_type: String,
    val adult: Boolean,
    val original_language: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val origin_country: List<String>
)

data class TmdbActor(
    val id: Int,
    val name: String,
    val original_name: String,
    val media_type: String,
    val adult: Boolean,
    val popularity: Double,
    val gender: Int,
    val known_for_department: String?,
    val profile_path: String
)

data class TmdbActorsResult(
    val page: Int,
    val results: List<TmdbActor>,
    val total_pages: Int,
    val total_results: Int
)