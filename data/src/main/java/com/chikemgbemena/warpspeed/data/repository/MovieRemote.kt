package com.chikemgbemena.warpspeed.data.repository

import com.chikemgbemena.warpspeed.data.models.MovieEntity

interface MovieRemote {
    suspend fun searchMovie(title: String): List<MovieEntity>
    suspend fun getMovieById(imdbId: String): MovieEntity
}