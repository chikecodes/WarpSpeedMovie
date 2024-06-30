package com.chikemgbemena.warpspeed.data

import com.chikemgbemena.warpspeed.data.mapper.MovieMapper
import com.chikemgbemena.warpspeed.data.source.MovieCacheDataSource
import com.chikemgbemena.warpspeed.data.source.MovieRemoteDataSource
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieCacheDataSource: MovieCacheDataSource,
    private val movieMapper: MovieMapper
): MovieRepository {

    override suspend fun searchMovie(title: String): Flow<List<Movie>> = flow {
        val isCached = movieCacheDataSource.isCached()
        val searchedMovies = movieCacheDataSource.searchMovie(title)

        val movies = if (searchedMovies.isNotEmpty() && isCached && !movieCacheDataSource.isExpired()) {
            movieCacheDataSource.searchMovie(title).map { movieEntity ->
                movieMapper.mapFromEntity(movieEntity)
            }
        } else {
            movieRemoteDataSource.searchMovie(title).map { movieEntity ->
                movieMapper.mapFromEntity(movieEntity)
            }
        }
        if (movies.isNotEmpty()) {
            saveMovies(movies)
        }
        emit(movies)
    }

    override suspend fun getMovieById(imdbId: String): Flow<Movie> = flow {
        val movie = movieRemoteDataSource.getMovieById(imdbId)
        emit(
            movieMapper.mapFromEntity(movie)
        )
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        val movieEntities = movies.map { movie ->
            movieMapper.mapToEntity(movie)
        }
        movieCacheDataSource.saveMovies(movieEntities)
    }
}