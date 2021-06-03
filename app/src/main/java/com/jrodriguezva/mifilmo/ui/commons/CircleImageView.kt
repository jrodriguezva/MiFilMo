package com.jrodriguezva.mifilmo.ui.commons

import android.content.Context
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import com.jrodriguezva.mifilmo.R

class CircleImageView(
    context: Context
) : AppCompatImageView(context) {

    init {
        outlineProvider = ViewOutlineProvider.BACKGROUND
        clipToOutline = true
        setBackgroundResource(R.drawable.circle_background)
        scaleType = ScaleType.CENTER_CROP
    }
}