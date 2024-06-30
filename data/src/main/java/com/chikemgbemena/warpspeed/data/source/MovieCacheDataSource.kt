package com.chikemgbemena.warpspeed.data.source

import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.data.repository.MovieCache
import javax.inject.Inject

class MovieCacheDataSource @Inject constructor(
    private val movieCache: MovieCache
)  {

    suspend fun searchMovie(title: String): List<MovieEntity> = movieCache.searchMovie(title)

    suspend fun saveMovies(movies: List<MovieEntity>) {
        movieCache.saveMovies(movies)
        movieCache.setLastCacheTime(System.currentTimeMillis())
    }

    suspend fun isCached(): Boolean = movieCache.isCached()

    suspend fun isExpired(): Boolean = movieCache.isExpired()

}