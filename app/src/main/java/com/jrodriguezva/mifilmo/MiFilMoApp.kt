package com.jrodriguezva.mifilmo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import com.jrodriguezva.mifilmo.framework.local.preferences.MODE_NIGHT_FOLLOW_SYSTEM
import com.jrodriguezva.mifilmo.framework.local.preferences.MODE_NIGHT_NO
import com.jrodriguezva.mifilmo.framework.local.preferences.MODE_NIGHT_YES
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MiFilMoApp : Application() {

    @Inject
    lateinit var preferenceDataSource: PreferenceDataSource

    override fun onCreate() {
        super.onCreate()
        changeTheme()
    }

    private fun changeTheme() {
        when (preferenceDataSource.getTheme()) {
            MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            MODE_NIGHT_FOLLOW_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}

