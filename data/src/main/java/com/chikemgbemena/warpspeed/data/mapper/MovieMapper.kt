package com.chikemgbemena.warpspeed.data.mapper

import com.chikemgbemena.warpspeed.data.models.MovieEntity
import javax.inject.Inject
import com.chikemgbemena.warpspeed.domain.models.Movie

class MovieMapper @Inject constructor(
) : Mapper<MovieEntity, Movie> {

    override fun mapFromEntity(type: MovieEntity): Movie {
        return Movie(
            title = type.title,
            year = type.year,
            poster = type.poster,
            imdbId = type.imdbId,
            type = type.type
        )
    }

    override fun mapToEntity(type: Movie): MovieEntity {
        return MovieEntity(
            title = type.title,
            year = type.year,
            poster = type.poster,
            imdbId = type.imdbId,
            type = type.type
        )
    }
}
