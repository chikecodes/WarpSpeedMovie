package com.chikemgbemena.warpspeed.presentation.viewmodel

import androidx.lifecycle.Observer
import com.chikemgbemena.warpspeed.domain.interactor.SearchMovieUseCase
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.presentation.fakes.FakeData
import com.chikemgbemena.warpspeed.presentation.utils.PresentationBaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchMovieViewModelTest:  PresentationBaseTest() {

    @Mock
    lateinit var searchMovieUseCase: SearchMovieUseCase

    @Mock
    private lateinit var observer: Observer<SearchMovieUIModel>

    private lateinit var sut: SearchMovieViewModel

    @Before
    fun setup() {
        sut = SearchMovieViewModel(dispatcher, searchMovieUseCase)
        sut.getMoviesLiveData().observeForever(observer)
    }

    @Test
    fun `search movie should return movie list from use-case`() =
        runTest(dispatcher.test) {

            // Arrange (Given)
            val title = "title"
            val movies = FakeData.getMovies()
            `when`(searchMovieUseCase(title)).thenReturn(movies)

            // Act (When)
            sut.searchMovie(title)
            advanceUntilIdle()

            // Assert (Then)
            verify(observer).onChanged(SearchMovieUIModel.Loading)
            verify(observer).onChanged(SearchMovieUIModel.Success(movies.single()))
        }

    @Test
    fun `search movie should return empty movie list from use-case`() =
        runTest(dispatcher.test) {
            // Arrange (Given)
            val title = "title"
            val movies = flowOf(emptyList<Movie>())
            `when`(searchMovieUseCase(title)).thenReturn(movies)

            // Act (When)
            sut.searchMovie(title)
            advanceUntilIdle()

            // Assert (Then)
            verify(observer).onChanged(SearchMovieUIModel.Loading)
            verify(observer).onChanged(SearchMovieUIModel.Success(movies.single()))
        }
}