package com.jrodriguezva.mifilmo.utils.extensions

import android.widget.TextView
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class ViewExtensionsKtTest {

    @Test
    fun fromMinutesToHHmm() {
        val view: TextView = mockk()
        view.fromMinutesToHHmm(0)

        verify {
            view.text = "0h 0m"
        }
    }
}