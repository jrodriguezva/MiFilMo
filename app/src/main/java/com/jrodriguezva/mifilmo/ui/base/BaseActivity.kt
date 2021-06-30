package com.jrodriguezva.mifilmo.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.jrodriguezva.mifilmo.di.AppModule
import com.jrodriguezva.mifilmo.utils.extensions.updateLocale
import dagger.hilt.android.EntryPointAccessors
import java.util.Locale

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val localeUpdatedContext: Context = changeLanguage(newBase)
        super.attachBaseContext(localeUpdatedContext)
    }

    private fun changeLanguage(newBase: Context): Context {
        val preferenceDataSource = EntryPointAccessors.fromApplication(
            newBase,
            AppModule.WrapperEntryPoint::class.java
        ).preferenceDataSource
        val localeToSwitchTo = preferenceDataSource.getLanguage()
        return newBase.updateLocale(Locale(localeToSwitchTo))
    }

}