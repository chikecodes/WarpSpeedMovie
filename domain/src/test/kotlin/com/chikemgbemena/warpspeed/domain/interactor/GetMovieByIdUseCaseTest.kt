package com.chikemgbemena.warpspeed.domain.interactor

import com.chikemgbemena.warpspeed.domain.fakes.FakeData
import com.chikemgbemena.warpspeed.domain.repository.MovieRepository
import com.chikemgbemena.warpspeed.domain.utils.DomainBaseTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
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
class GetMovieByIdUseCaseTest : DomainBaseTest()  {

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var getMovieByIdUseCase: GetMovieByIdUseCase

    @Before
    fun setUp() {
        getMovieByIdUseCase = GetMovieByIdUseCase(movieRepository)
    }

    @Test
    fun `get movie with id should return success result with movie`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieId = "tt0816692"
            val expectedMovie = FakeData.getMovie()
            whenever(movieRepository.getMovieById(movieId)) doReturn expectedMovie

            // Act (When)
            val movie = getMovieByIdUseCase(movieId).single()

            // Assert (Then)
            assertEquals(expectedMovie.single(), movie)
            verify(movieRepository, times(1)).getMovieById(movieId)
        }

    @Test
    fun `get movie with id should return success result with exception`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieId = "tt0816692"
            whenever(movieRepository.getMovieById(movieId)) doAnswer { throw IOException() }

            // Act (When)
            var exception: IOException? = null
            try {
                getMovieByIdUseCase(movieId).single()
            } catch (e: IOException) {
                exception = e
            }

            // Assert (Then)
            assertNotNull(exception)
            verify(movieRepository, times(1)).getMovieById(movieId)
        }
}