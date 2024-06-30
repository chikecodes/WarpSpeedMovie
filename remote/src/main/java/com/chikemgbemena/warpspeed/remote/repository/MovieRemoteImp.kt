package com.chikemgbemena.warpspeed.remote.repository

import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.data.repository.MovieRemote
import com.chikemgbemena.warpspeed.remote.api.MovieService
import com.chikemgbemena.warpspeed.remote.mappers.MovieEntityMapper
import javax.inject.Inject
import javax.inject.Named

class MovieRemoteImp @Inject constructor(
    private val movieService: MovieService,
    private val movieEntityMapper: MovieEntityMapper,
    @Named("omdbApiKey") private val apiKey: String
): MovieRemote {

    override suspend fun searchMovie(title: String): List<MovieEntity> =
        movieService.searchMovies(title, apiKey).search?.map { movie ->
            movieEntityMapper.mapFromModel(movie)
        } ?: emptyList()

    override suspend fun getMovieById(imdbId: String): MovieEntity =
        movieEntityMapper.mapFromModel(movieService.getMovieDetails(imdbId, apiKey))
}