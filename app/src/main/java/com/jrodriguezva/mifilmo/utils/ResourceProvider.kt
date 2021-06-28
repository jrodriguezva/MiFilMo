package com.jrodriguezva.mifilmo.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(@ApplicationContext private var context: Context) {
    fun getString(@StringRes stringResId: Int): String {
        return context.resources.getString(stringResId)
    }
}