package com.jrodriguezva.mifilmo.utils.extensions

import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.ui.commons.EndlessScrollView

fun RecyclerView.endless(visibleThreshold: Int = 10, loadMore: () -> Unit) {
    this.addOnScrollListener(EndlessScrollView(this, visibleThreshold, loadMore))
}