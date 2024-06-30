package com.chikemgbemena.warpspeed.domain.repository

import com.chikemgbemena.warpspeed.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    // Remote
    suspend fun searchMovie(title: String): Flow<List<Movie>>
    suspend fun getMovieById(imdbId: String): Flow<Movie>

    // Cache
    suspend fun saveMovies(movies: List<Movie>)
}