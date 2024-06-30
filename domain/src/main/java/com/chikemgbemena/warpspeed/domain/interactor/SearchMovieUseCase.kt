package com.chikemgbemena.warpspeed.domain.interactor

import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias SearchMovieBaseUseCase = BaseUseCase<String, Flow<List<Movie>>>

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : SearchMovieBaseUseCase {

    override suspend operator fun invoke(params: String) = movieRepository.searchMovie(params)
}