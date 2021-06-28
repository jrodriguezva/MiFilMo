package com.jrodriguezva.mifilmo.utils.helper

import com.jrodriguezva.mifilmo.utils.helper.TmdbImageSizes.backdropSizes
import com.jrodriguezva.mifilmo.utils.helper.TmdbImageSizes.baseImageUrl
import com.jrodriguezva.mifilmo.utils.helper.TmdbImageSizes.logoSizes
import com.jrodriguezva.mifilmo.utils.helper.TmdbImageSizes.posterSizes
import com.jrodriguezva.mifilmo.utils.helper.TmdbImageSizes.profileSizes


object TmdbImageUrlProvider {
    private val IMAGE_SIZE_PATTERN = "w(\\d+)$".toRegex()

    fun getPosterUrl(path: String, imageWidth: Int): String {
        return "$baseImageUrl${selectSize(posterSizes, imageWidth)}$path"
    }

    fun getBackdropUrl(path: String, imageWidth: Int): String {
        return "$baseImageUrl${selectSize(backdropSizes, imageWidth)}$path"
    }

    fun getProfileUrl(path: String, imageWidth: Int): String {
        return "$baseImageUrl${selectSize(profileSizes, imageWidth)}$path"
    }

    fun getLogoUrl(path: String, imageWidth: Int): String {
        return "$baseImageUrl${selectSize(logoSizes, imageWidth)}$path"
    }

    private fun selectSize(sizes: List<String>, imageWidth: Int): String {
        var previousSize: String? = null
        var previousWidth = 0
        if (imageWidth <= 0) return sizes.last()

        for (i in sizes.indices) {
            val size = sizes[i]
            val sizeWidth = extractWidthAsIntFrom(size) ?: continue

            if (sizeWidth > imageWidth) {
                if (previousSize != null && imageWidth > (previousWidth + sizeWidth) / 2) {
                    return size
                } else if (previousSize != null) {
                    return previousSize
                }
            } else if (i == sizes.size - 1) {
                if (imageWidth < sizeWidth * 2) {
                    return size
                }
            }

            previousSize = size
            previousWidth = sizeWidth
        }

        return previousSize ?: sizes.last()
    }

    private fun extractWidthAsIntFrom(size: String): Int? {
        return IMAGE_SIZE_PATTERN.matchEntire(size)?.groups?.get(1)?.value?.toInt()
    }
}
