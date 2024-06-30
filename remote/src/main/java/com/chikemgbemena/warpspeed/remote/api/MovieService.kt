package com.chikemgbemena.warpspeed.remote.api

import com.chikemgbemena.warpspeed.remote.models.Movie
import com.chikemgbemena.warpspeed.remote.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    suspend fun searchMovies(
        @Query("s") movieTitle: String,
        @Query("apikey") apiKey: String
    ): MovieResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") movieId: String,
        @Query("apikey") apiKey: String
    ): Movie
}
