package com.jrodriguezva.mifilmo.ui.commons

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.jrodriguezva.mifilmo.R

class RatingImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var rating: Float = DEFAULT_RATING

    fun setRating(rating: Float) {
        when {
            rating < 1 -> setImageResource(R.drawable.ic_crying)
            rating < 2.5 -> setImageResource(R.drawable.ic_crying_1)
            rating < 4 -> setImageResource(R.drawable.ic_sad)
            rating < 6 -> setImageResource(R.drawable.ic_confused)
            rating < 7.5 -> setImageResource(R.drawable.ic_smile)
            rating < 9 -> setImageResource(R.drawable.ic_in_love_1)
            rating < 10 -> setImageResource(R.drawable.ic_in_love)
        }
    }

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.RatingImageView)
            with(a) {
                rating = getFloat(R.styleable.RatingImageView_rating, DEFAULT_RATING)
                recycle()
            }
        }

        setImageResource(R.drawable.ic_in_love)
    }


    companion object {
        const val DEFAULT_RATING = 5F
    }
}