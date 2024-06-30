package com.chikemgbemena.warpspeed.remote.models

import com.squareup.moshi.Json

data class MovieResponse(
    @field:Json(name = "Search")
    val search: List<Movie>?,
    val totalResults: String?,
    @field:Json(name = "Response")
    val response: String
)

data class Movie (

    @field:Json(name = "Title")
    val title: String,
    @field:Json(name = "Year")
    val year: String,
    val rated: String? = null,
    val released: String? = null,
    val runtime: String? = null,
    val genre: String? = null,
    val director: String? = null,
    val writer: String? = null,
    val actors: String? = null,
    val plot: String? = null,
    val language: String? = null,
    val country: String? = null,
    val awards: String? = null,
    @field:Json(name = "Poster")
    val poster: String,
    val ratings: List<Rating>? = null,
    val metascore: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val imdbID: String,
    @field:Json(name = "Type")
    val type: String,
    val dvd: String? = null,
    val boxOffice: String? = null,
    val production: String? = null,
    val website: String? = null,
    val response: String? = null
)

data class Rating(
    val source: String,
    val value: String,
)