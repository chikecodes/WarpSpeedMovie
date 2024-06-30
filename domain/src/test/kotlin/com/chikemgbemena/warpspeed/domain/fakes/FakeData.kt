package com.chikemgbemena.warpspeed.domain.fakes

import com.chikemgbemena.warpspeed.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeData {

    fun getMovies(): Flow<List<Movie>> = flow {
        val movies = listOf(
            Movie(
                title = "Interstellar",
                year = "2014",
                imdbId = "tt0816692",
                type = "movie",
                poster = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
            )
        )
        emit(movies)
    }

    fun getMovie(): Flow<Movie> = flow {
        emit(Movie(
            title = "Interstellar",
            year = "2014",
            imdbId = "tt0816692",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
        ))
    }
}
