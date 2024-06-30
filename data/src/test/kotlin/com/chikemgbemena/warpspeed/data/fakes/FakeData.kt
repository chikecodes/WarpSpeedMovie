package com.chikemgbemena.warpspeed.data.fakes

import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeData {

    fun getMovies(): List<MovieEntity> =
        listOf(
            MovieEntity(
                title = "Interstellar",
                year = "2014",
                imdbId = "tt0816692",
                type = "movie",
                poster = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
            )
        )

    fun getMovie(): Flow<MovieEntity> = flow {
        emit(MovieEntity(
            title = "Interstellar",
            year = "2014",
            imdbId = "tt0816692",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
        ))
    }
}
