package com.chikemgbemena.warpspeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chikemgbemena.warpspeed.presentation.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + contextProvider.main)

    abstract val coroutineExceptionHandler: CoroutineExceptionHandler

    protected fun launchCoroutine(
        dispatcher: CoroutineDispatcher,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            block()
        }
    }

    fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
        return launchCoroutine(contextProvider.io, block)
    }

    fun launchMain(block: suspend CoroutineScope.() -> Unit): Job {
        return launchCoroutine(contextProvider.main, block)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

