package com.jrodriguezva.mifilmo.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToBirthday(): String {
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this)
}

fun Date.formatLarge(): String {
    return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(this)

}