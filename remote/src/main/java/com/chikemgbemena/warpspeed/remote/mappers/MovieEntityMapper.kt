package com.chikemgbemena.warpspeed.remote.mappers

import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.remote.models.Movie
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() : EntityMapper<Movie, MovieEntity> {

    override fun mapFromModel(model: Movie): MovieEntity {
        return MovieEntity(
           title = model.title,
            year = model.year,
            poster = model.poster,
            imdbId = model.imdbID,
            type = model.type
        )
    }
}