package com.jrodriguezva.mifilmo.utils.extensions

import android.widget.TextView
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class ViewExtensionsKtTest {

    @Test
    fun `fromMinutesToHHmm when minutes are 0 should show 0h 0m`() {
        val view: TextView = mockk()
        val slot = slot<String>()
        every { view.text = capture(slot) }.answers { println(slot.captured) }

        view.fromMinutesToHHmm(0)

        assertEquals("0h 0m", slot.captured)
    }

    @Test
    fun `fromMinutesToHHmm when minutes are 630 should show 10h 30m`() {
        val view: TextView = mockk()
        val slot = slot<String>()
        every { view.text = capture(slot) }.answers { println(slot.captured) }

        view.fromMinutesToHHmm(630)

        assertEquals("10h 30m", slot.captured)
    }
}