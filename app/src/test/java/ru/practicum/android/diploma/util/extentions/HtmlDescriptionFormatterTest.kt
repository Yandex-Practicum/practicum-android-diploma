package ru.practicum.android.diploma.util.extentions

import org.junit.Assert.assertEquals
import org.junit.Test

class HtmlDescriptionFormatterTest {

    @Test
    fun `formatHtmlDescription returns empty string for null`() {
        assertEquals("", null.formatHtmlDescription())
    }

    @Test
    fun `formatHtmlDescription returns empty string for blank`() {
        assertEquals("", "   ".formatHtmlDescription())
    }
}
