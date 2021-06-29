package com.jrodriguezva.mifilmo.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.Locale

fun Context?.restartApp() {
    this?.let { context ->
        val restartAppIntent = Intent(context, context::class.java)
        restartAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(restartAppIntent)
        (context as Activity).finish()
        context.overridePendingTransition(0, 0)
    }
}

@Suppress("DEPRECATION")
fun Context.updateLocale(localeToSwitchTo: Locale): Context {
    var context = this
    val resources: Resources = context.resources
    val configuration: Configuration = resources.configuration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val localeList = LocaleList(localeToSwitchTo)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
    } else {
        configuration.locale = localeToSwitchTo
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        context = context.createConfigurationContext(configuration)
    } else {
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    return context
}

