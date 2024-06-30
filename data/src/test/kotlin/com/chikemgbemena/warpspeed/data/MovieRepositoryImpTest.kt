package com.chikemgbemena.warpspeed.data

import com.chikemgbemena.warpspeed.data.fakes.FakeData
import com.chikemgbemena.warpspeed.data.mapper.MovieMapper
import com.chikemgbemena.warpspeed.data.models.MovieEntity
import com.chikemgbemena.warpspeed.data.source.MovieCacheDataSource
import com.chikemgbemena.warpspeed.data.source.MovieRemoteDataSource
import com.chikemgbemena.warpspeed.data.utils.DataBaseTest
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImpTest: DataBaseTest() {

    @Mock
    lateinit var movieRemoteDataSource: MovieRemoteDataSource
    @Mock
    lateinit var movieCacheDataSource: MovieCacheDataSource
    @Mock
    lateinit var movieMapper: MovieMapper

    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = MovieRepositoryImp(
            movieRemoteDataSource = movieRemoteDataSource,
            movieCacheDataSource = movieCacheDataSource,
            movieMapper = movieMapper
        )
    }

    @Test
    fun `searchMovie should return cached movies when cache is valid`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieTitle = "Django Unchained"
            val expectedMovies = FakeData.getMovies()
            val movie: Movie = mock()
            val movieEntity: MovieEntity = mock()

            whenever(movieCacheDataSource.isCached()) doReturn true
            whenever(movieCacheDataSource.isExpired()) doReturn false
            whenever(movieCacheDataSource.searchMovie(movieTitle)) doReturn expectedMovies
            whenever(movieMapper.mapFromEntity(any<MovieEntity>())) doReturn movie
            whenever(movieMapper.mapToEntity(any<Movie>())) doReturn movieEntity

            // Act (When)
            val result = movieRepository.searchMovie(movieTitle).first()

            // Assert (Then)
            assertEquals(listOf(movie), result)
            verify(movieCacheDataSource).isCached()
            verify(movieCacheDataSource).isExpired()
            verify(movieCacheDataSource).searchMovie(movieTitle)
            verify(movieMapper, times(1)).mapFromEntity(any<MovieEntity>())
            verify(movieCacheDataSource, times(1)).saveMovies(listOf(movieEntity))
        }

    @Test
    fun `searchMovie should return remote movies when cache is invalid`() =  runTest(dispatcher) {

        // Arrange
        val movieTitle = "Interstellar"
        val expectedMovies = FakeData.getMovies()
        val movie: Movie = mock()

        `when`(movieCacheDataSource.isCached()).thenReturn(false)
        `when`(movieRemoteDataSource.searchMovie(movieTitle)).thenReturn(expectedMovies)
        `when`(movieMapper.mapFromEntity(any<MovieEntity>())).thenReturn(movie)

        whenever(movieMapper.mapToEntity(any<Movie>())) doReturn expectedMovies.first()

        // Act
        val result = movieRepository.searchMovie(movieTitle).first()

        // Assert
        assertEquals(listOf(movie), result)
        verify(movieCacheDataSource).isCached()
        verify(movieRemoteDataSource).searchMovie(movieTitle)
        verify(movieMapper, times(1)).mapFromEntity(any<MovieEntity>())
        verify(movieCacheDataSource, never()).isExpired()

        verify(movieCacheDataSource, times(1)).saveMovies(expectedMovies)
    }

}