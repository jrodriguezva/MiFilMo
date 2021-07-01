package com.jrodriguezva.mifilmo.utils.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.toDate(): Date {
    return try {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this) ?: Date()
    } catch (exception: ParseException) {
        Date()
    }
}

fun Date.formatToBirthday(): String {
    return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(this)

}