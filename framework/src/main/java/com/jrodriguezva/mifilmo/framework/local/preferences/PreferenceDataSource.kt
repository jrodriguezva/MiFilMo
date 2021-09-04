package com.jrodriguezva.mifilmo.framework.local.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource

class PreferenceDataSourceImpl(private val context: Context) : PreferenceDataSource {

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun getLanguage(): String {
        return sharedPreferences.getString(PREFERENCE_LANGUAGE, PREFERENCE_LANGUAGE_DEFAULT)
            .orEmpty()
    }

    override fun getTheme(): String {
        return sharedPreferences.getString(PREFERENCE_THEME, MODE_NIGHT_FOLLOW_SYSTEM).orEmpty()
    }

}