package com.chikemgbemena.warpspeed.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

open class PresentationPreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.chikemgbemena.warpspeed.presentation.preferences"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

}
