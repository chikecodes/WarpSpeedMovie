package com.chikemgbemena.warpspeed.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.chikemgbemena.warpspeed.domain.interactor.GetMovieByIdUseCase
import com.chikemgbemena.warpspeed.domain.interactor.SearchMovieUseCase
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.presentation.utils.CoroutineContextProvider
import com.chikemgbemena.warpspeed.presentation.utils.ExceptionHandler
import com.chikemgbemena.warpspeed.presentation.utils.UiAwareLiveData
import com.chikemgbemena.warpspeed.presentation.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class GetMovieDetailUIModel : UiAwareModel() {
    data object Loading : GetMovieDetailUIModel()
    data class Error(@StringRes val error: Int) : GetMovieDetailUIModel()
    data class Success(val data: Movie) : GetMovieDetailUIModel()
}

@HiltViewModel
class GetMovieViewModel @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : BaseViewModel(coroutineContextProvider) {

    private val _movieDetailLiveData = UiAwareLiveData<GetMovieDetailUIModel>()
    private var movieDetailLiveData: LiveData<GetMovieDetailUIModel> = _movieDetailLiveData

    fun getMovieDetailLiveData(): LiveData<GetMovieDetailUIModel> = movieDetailLiveData

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val errorMessage = ExceptionHandler.parse(exception)
        _movieDetailLiveData.postValue(GetMovieDetailUIModel.Error(errorMessage))
    }

    fun getMovieDetail(imdbId: String) {
        _movieDetailLiveData.postValue(GetMovieDetailUIModel.Loading)
        launchIO {
            getMovieByIdUseCase(imdbId).collect {
                _movieDetailLiveData.postValue(GetMovieDetailUIModel.Success(it))
            }
        }
    }
}