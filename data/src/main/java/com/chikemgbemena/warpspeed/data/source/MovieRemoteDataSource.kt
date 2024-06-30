package com.chikemgbemena.warpspeed.data.source

import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.data.repository.MovieRemote
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieRemote: MovieRemote,
) {
    suspend fun searchMovie(title: String): List<MovieEntity> =
        movieRemote.searchMovie(title)

    suspend fun getMovieById(imdbId: String): MovieEntity =
        movieRemote.getMovieById(imdbId)
}