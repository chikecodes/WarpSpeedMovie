package com.chikemgbemena.warpspeed.cache

import com.chikemgbemena.warpspeed.cache.dao.MovieDao
import com.chikemgbemena.warpspeed.cache.mapper.MovieCacheMapper
import com.chikemgbemena.warpspeed.cache.utils.CachePreferencesHelper
import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.data.repository.MovieCache
import javax.inject.Inject

class MovieCacheImp @Inject constructor(
    private val movieDao: MovieDao,
    private val movieCacheMapper: MovieCacheMapper,
    private val preferencesHelper: CachePreferencesHelper
) : MovieCache {

    override suspend fun searchMovie(title: String): List<MovieEntity> =
        movieDao.searchMovies(title).map { movieEntity ->
            movieCacheMapper.mapFromCached(movieEntity)
        }

    override suspend fun saveMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(
            movies.map {
                movieCacheMapper.mapToCached(it)
            }
        )
    }

    override suspend fun isCached(): Boolean = movieDao.getMovies().isNotEmpty()

    override suspend fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long = preferencesHelper.lastCacheTime

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}
