package com.chikemgbemena.warpspeed.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.chikemgbemena.warpspeed.domain.interactor.SearchMovieUseCase
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.presentation.utils.CoroutineContextProvider
import com.chikemgbemena.warpspeed.presentation.utils.ExceptionHandler
import com.chikemgbemena.warpspeed.presentation.utils.UiAwareLiveData
import com.chikemgbemena.warpspeed.presentation.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class SearchMovieUIModel : UiAwareModel() {
    data object Loading : SearchMovieUIModel()
    data class Error(@StringRes val error: Int) : SearchMovieUIModel()
    data class Success(val data: List<Movie>) : SearchMovieUIModel()
}

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel(coroutineContextProvider) {

    private val _movieListLiveData = UiAwareLiveData<SearchMovieUIModel>()
    private var movieListLiveData: LiveData<SearchMovieUIModel> = _movieListLiveData

    fun getMoviesLiveData(): LiveData<SearchMovieUIModel> = movieListLiveData

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val errorMessage = ExceptionHandler.parse(exception)
        _movieListLiveData.postValue(SearchMovieUIModel.Error(errorMessage))
    }

    fun searchMovie(title: String) {
        _movieListLiveData.postValue(SearchMovieUIModel.Loading)
        launchIO {
            searchMovieUseCase(title).collect {
                _movieListLiveData.postValue(SearchMovieUIModel.Success(it))
            }
        }
    }
}