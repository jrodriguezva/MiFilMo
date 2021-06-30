package com.jrodriguezva.mifilmo.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.framework.local.preferences.MODE_NIGHT_FOLLOW_SYSTEM
import com.jrodriguezva.mifilmo.framework.local.preferences.MODE_NIGHT_NO
import com.jrodriguezva.mifilmo.framework.local.preferences.MODE_NIGHT_YES
import com.jrodriguezva.mifilmo.framework.local.preferences.PREFERENCE_LANGUAGE
import com.jrodriguezva.mifilmo.framework.local.preferences.PREFERENCE_THEME
import com.jrodriguezva.mifilmo.utils.extensions.restartApp

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        preferenceManager.findPreference<ListPreference>(PREFERENCE_LANGUAGE)
            ?.setOnPreferenceChangeListener { _, _ ->
                context?.restartApp()
                true
            }

        preferenceManager.findPreference<ListPreference>(PREFERENCE_THEME)
            ?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue as? String) {
                    MODE_NIGHT_NO -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MODE_NIGHT_YES -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MODE_NIGHT_FOLLOW_SYSTEM -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                true
            }
    }
}