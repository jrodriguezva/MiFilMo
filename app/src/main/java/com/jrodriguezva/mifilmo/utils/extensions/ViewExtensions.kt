package com.jrodriguezva.mifilmo.utils.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.ImageLoader
import coil.imageLoader
import coil.loadAny
import coil.request.Disposable
import coil.request.ImageRequest
import com.jrodriguezva.mifilmo.utils.helper.TmdbImageUrlProvider
import java.util.concurrent.TimeUnit

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun TextView.fromMinutesToHHmm(minutes: Int) {
    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
    text = String.format("%dh %dm", hours, remainMinutes)
}

@JvmSynthetic
inline fun ImageView.loadPoster(
    uri: String,
    imageWidth: Int = 0,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {},
): Disposable = loadAny(TmdbImageUrlProvider.getPosterUrl(uri, imageWidth), imageLoader, builder)

@JvmSynthetic
inline fun ImageView.loadProfile(
    uri: String,
    imageWidth: Int = 0,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {},
): Disposable = loadAny(TmdbImageUrlProvider.getProfileUrl(uri, imageWidth), imageLoader, builder)

@JvmSynthetic
inline fun ImageView.loadBackdrop(
    uri: String,
    imageWidth: Int = 0,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {},
): Disposable = loadAny(TmdbImageUrlProvider.getBackdropUrl(uri, imageWidth), imageLoader, builder)
