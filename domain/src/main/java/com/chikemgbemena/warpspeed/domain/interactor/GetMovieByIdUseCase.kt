package com.chikemgbemena.warpspeed.domain.interactor

import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetMovieByIdBaseUseCase = BaseUseCase<String, Flow<Movie>>

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieByIdBaseUseCase {

    override suspend operator fun invoke(params: String) = movieRepository.getMovieById(params)
}