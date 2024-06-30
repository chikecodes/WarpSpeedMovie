package com.chikemgbemena.warpspeed.data.repository

import com.chikemgbemena.warpspeed.data.models.MovieEntity

interface MovieCache {

    suspend fun searchMovie(title: String): List<MovieEntity>
    suspend fun saveMovies(movies: List<MovieEntity>)
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}