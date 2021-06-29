package com.jrodriguezva.mifilmo.data.datasource.preferences

interface PreferenceDataSource {
    fun getLanguage(): String
    fun getTheme(): String
}