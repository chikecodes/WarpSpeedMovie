package com.chikemgbemena.warpspeed.data.source

import com.chikemgbemena.warpspeed.data.fakes.FakeData
import com.chikemgbemena.warpspeed.data.repository.MovieCache
import com.chikemgbemena.warpspeed.data.utils.DataBaseTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieCacheDataSourceTest : DataBaseTest()  {

    @Mock
    lateinit var movieCache: MovieCache

    lateinit var movieCacheDataSource: MovieCacheDataSource

    @Before
    fun setUp() {
        movieCacheDataSource = MovieCacheDataSource(movieCache)
    }

    @Test
    fun `search movie with title should return success result with movies`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieTitle = "Django Unchained"
            val expectedMovies = FakeData.getMovies()
            whenever(movieCache.searchMovie(movieTitle)) doReturn expectedMovies

            // Act (When)
            val movies = movieCacheDataSource.searchMovie(movieTitle).single()

            // Assert (Then)
            assertEquals(expectedMovies.single(), movies)
            verify(movieCache, times(1)).searchMovie(movieTitle)
        }

    @Test
    fun `search movie with title should return success result with exception`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movieTitle = "Django Unchained"
            whenever(movieCache.searchMovie(movieTitle)) doAnswer { throw IOException() }

            // Act (When)
            var exception: IOException? = null
            try {
                movieCacheDataSource.searchMovie(movieTitle).single()
            } catch (e: IOException) {
                exception = e
            }

            // Assert (Then)
            assertNotNull(exception)
            verify(movieCache, times(1)).searchMovie(movieTitle)
        }

    @Test
    fun `save movies should return save in cache result`() =
        runTest(dispatcher) {
            // Arrange (Given)
            val movies = FakeData.getMovies()
            whenever(movieCache.saveMovies(movies)) doReturn Unit

            // Act (When)
            movieCacheDataSource.saveMovies(movies)

            // Assert (Then)
            verify(movieCache, times(1)).saveMovies(movies)
            verify(movieCache, times(1)).setLastCacheTime(any())
        }
}