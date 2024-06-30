package com.chikemgbemena.warpspeed.domain.utils

import com.aqube.ram.domain.interactor.CoroutineTestRule
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class DomainBaseTest {
    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = CoroutineTestRule()

    val dispatcher = testRule.dispatcher

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
}
