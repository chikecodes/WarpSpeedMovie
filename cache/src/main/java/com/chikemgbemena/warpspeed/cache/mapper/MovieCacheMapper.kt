package com.chikemgbemena.warpspeed.cache.mapper

import com.chikemgbemena.warpspeed.cache.models.MovieCacheEntity
import com.chikemgbemena.warpspeed.data.models.MovieEntity
import javax.inject.Inject

class MovieCacheMapper @Inject constructor(
) : CacheMapper<MovieCacheEntity, MovieEntity> {

    override fun mapFromCached(type: MovieCacheEntity): MovieEntity {
        return MovieEntity(
           title = type.title,
            year = type.year,
            poster = type.poster,
            imdbId = type.imdbId,
            type = type.type
        )
    }

    override fun mapToCached(type: MovieEntity): MovieCacheEntity {
        return MovieCacheEntity(
            title = type.title,
            year = type.year,
            poster = type.poster,
            imdbId = type.imdbId,
            type = type.type
        )
    }
}
