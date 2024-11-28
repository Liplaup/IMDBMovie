package com.example.myapplication

// Classe représentant le résultat d'une requête pour les films, avec des informations sur la page et la liste des films.
data class TmdbMoviesResult(
    val page: Int,  // Numéro de la page actuelle
    val results: List<TmdbMovie>,  // Liste des films retournés
    val total_pages: Int,  // Nombre total de pages de résultats
    val total_results: Int  // Nombre total de films
)

// Classe représentant un film spécifique avec ses détails.
data class TmdbMovie(
    val adult: Boolean,  // Indique si le film est destiné à un public adulte
    val backdrop_path: String,  // Chemin de l'image de fond du film
    val genre_ids: List<Int>,  // Liste des identifiants des genres du film
    val id: Int,  // ID unique du film
    val original_language: String,  // Langue originale du film
    val original_title: String,  // Titre original du film
    val overview: String,  // Description ou synopsis du film
    val popularity: Double,  // Indicateur de popularité du film
    val poster_path: String,  // Chemin de l'affiche du film
    val release_date: String,  // Date de sortie du film
    val title: String,  // Titre du film
    val video: Boolean,  // Indique si le film est un vidéo en streaming
    val vote_average: Double,  // Moyenne des votes du film
    val vote_count: Int,  // Nombre total de votes
    val genres: List<Genre>,  // Liste des genres associés au film
    val runtime: Int,  // Durée du film en minutes
    val budget: Long,  // Budget du film
    val revenue: Long,  // Revenus du film
    var isFav: Boolean = false  // Indique si le film est marqué comme favori, valeur par défaut : false
)

// Classe représentant le résultat d'une requête pour les séries, avec des informations sur la page et la liste des séries.
data class TmdbSeriesResult(
    val page: Int,  // Numéro de la page actuelle
    val results: List<TmdbSeries>,  // Liste des séries retournées
    val total_pages: Int,  // Nombre total de pages de résultats
    val total_results: Int  // Nombre total de séries
)

// Classe représentant une série spécifique avec ses détails.
data class TmdbSeries(
    val id: Int,  // ID unique de la série
    val name: String,  // Nom de la série
    val overview: String,  // Description ou synopsis de la série
    val poster_path: String,  // Chemin de l'affiche de la série
    val backdrop_path: String,  // Chemin de l'image de fond de la série
    val vote_average: Double,  // Moyenne des votes de la série
    val vote_count: Int,  // Nombre total de votes pour la série
    val first_air_date: String,  // Date de première diffusion de la série
    val original_name: String,  // Nom original de la série
    val media_type: String,  // Type de média (ex. série, film)
    val adult: Boolean,  // Indique si la série est destinée à un public adulte
    val original_language: String,  // Langue originale de la série
    val genre_ids: List<Int>,  // Liste des identifiants des genres de la série
    val popularity: Double,  // Indicateur de popularité de la série
    val origin_country: List<String>,  // Liste des pays d'origine de la série
    val genres: List<Genre>,  // Liste des genres associés à la série
    var isFav: Boolean = false  // Indique si la série est marquée comme favori, valeur par défaut : false
)

// Classe représentant un acteur avec ses informations personnelles et ses rôles.
data class TmdbActor(
    val id: Int,  // ID unique de l'acteur
    val name: String,  // Nom de l'acteur
    val original_name: String,  // Nom original de l'acteur
    val media_type: String,  // Type de média (ex. acteur dans un film ou une série)
    val adult: Boolean,  // Indique si l'acteur a joué dans des productions adultes
    val popularity: Double,  // Indicateur de popularité de l'acteur
    val gender: Int,  // Genre de l'acteur (0 = inconnu, 1 = homme, 2 = femme)
    val profile_path: String,  // Chemin de l'image de profil de l'acteur
    val character: String,  // Rôle joué par l'acteur dans la production
    var isFav: Boolean = false  // Indique si l'acteur est marqué comme favori, valeur par défaut : false
)

// Classe représentant le résultat d'une requête pour les acteurs, avec des informations sur la page et la liste des acteurs.
data class TmdbActorsResult(
    val page: Int,  // Numéro de la page actuelle
    val results: List<TmdbActor>,  // Liste des acteurs retournés
    val total_pages: Int,  // Nombre total de pages de résultats
    val total_results: Int  // Nombre total d'acteurs
)

// Classe représentant un genre, qui est associé aux films, séries et acteurs.
data class Genre(
    val id: Int,  // ID unique du genre
    val name: String  // Nom du genre (ex. Action, Drame, Comédie, etc.)
)

// Classe représentant une liste d'acteurs associés à un film ou une série.
data class ListActor(
    val cast: List<TmdbActor>  // Liste des acteurs dans le film ou la série
)

data class Playlist(
    val checksum: String,
    val collaborative: Boolean,
    val cover: String,
    val creation_date: String,
    val creator: Creator,
    val description: String,
    val duration: Int,
    val fans: Int,
    val id: Int,
    val is_loved_track: Boolean,
    val link: String,
    val md5_image: String,
    val nb_tracks: Int,
    val picture_type: String,
    val `public`: Boolean,
    val share: String,
    val title: String,
    val tracklist: String,
    val tracks: Tracks,
    val type: String
)

data class Creator(
    val id: Int,
    val name: String,
    val tracklist: String,
    val type: String
)

data class Tracks(
    val checksum: String,
    val `data`: List<Data>
)

data class Data(
    val album: Album,
    val artist: Artist,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    val id: Long,
    val isrc: String,
    val link: String,
    val md5_image: String,
    val preview: String,
    val rank: Int,
    val readable: Boolean,
    val time_add: Int,
    val title: String,
    val title_short: String,
    val title_version: String,
    val type: String
)

data class Album(
    val cover: String,
    val id: Int,
    val md5_image: String,
    val title: String,
    val tracklist: String,
    val type: String,
    val upc: String
)

data class Artist(
    val id: Int,
    val link: String,
    val name: String,
    val tracklist: String,
    val type: String
)
