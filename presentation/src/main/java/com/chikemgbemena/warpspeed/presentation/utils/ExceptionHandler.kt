package com.chikemgbemena.warpspeed.presentation.utils

import androidx.annotation.StringRes
import com.chikemgbemena.warpspeedmovies.R
import java.net.UnknownHostException

internal object ExceptionHandler {

    @StringRes
    fun parse(t: Throwable): Int {
        return when (t) {
            is UnknownHostException -> R.string.error_check_internet_connection
            else -> R.string.error_oops_error_occurred
        }
    }
}
