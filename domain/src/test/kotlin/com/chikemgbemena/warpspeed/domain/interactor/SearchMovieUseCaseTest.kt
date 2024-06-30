package com.chikemgbemena.warpspeed.domain.interactor

import com.chikemgbemena.warpspeed.domain.fakes.FakeData
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import com.chikemgbemena.warpspeed.domain.utils.DomainBaseTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchMovieUseCaseTest : DomainBaseTest()  {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var searchMovieUseCase: SearchMovieUseCase

    @Before
    fun setUp() {
        searchMovieUseCase = SearchMovieUseCase(movieRepository)
    }

    @Test
    fun `search movie with title should return success result with movie list`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieTitle = "Interstellar"
            val expectedMovies = FakeData.getMovies()
            whenever(movieRepository.searchMovie(movieTitle)) doReturn expectedMovies

            // Act (When)
            val movies = searchMovieUseCase(movieTitle).single()

            // Assert (Then)
            assertEquals(expectedMovies.single(), movies)
            verify(movieRepository, times(1)).searchMovie(movieTitle)
        }

    @Test
    fun `search movie with title should return success result with exception`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieTitle = "Interstellar"
            whenever(movieRepository.searchMovie(movieTitle)) doAnswer { throw IOException() }

            // Act (When)
            var exception: IOException? = null
            try {
                searchMovieUseCase(movieTitle).single()
            } catch (e: IOException) {
                exception = e
            }

            // Assert (Then)
            assertNotNull(exception)
            verify(movieRepository, times(1)).searchMovie(movieTitle)
        }
}