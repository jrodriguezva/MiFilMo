package com.jrodriguezva.mifilmo.utils.extensions

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap.toBase64(): String {
    val baos = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}