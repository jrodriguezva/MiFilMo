package com.jrodriguezva.mifilmo.framework.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date {
    return try {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this) ?: Date()
    } catch (exception: ParseException) {
        Date()
    }
}