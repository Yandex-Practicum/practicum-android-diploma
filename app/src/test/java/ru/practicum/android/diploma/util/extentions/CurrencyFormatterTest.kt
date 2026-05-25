package ru.practicum.android.diploma.util.extentions

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFormatterTest {

    @Test
    fun `toCurrencySymbol maps known currencies`() {
        assertEquals("₽", "RUB".toCurrencySymbol())
        assertEquals("$", "USD".toCurrencySymbol())
        assertEquals("€", "EUR".toCurrencySymbol())
    }

    @Test
    fun `toCurrencySymbol returns empty string for unknown currency`() {
        assertEquals("", "ABC".toCurrencySymbol())
        assertEquals("", null.toCurrencySymbol())
    }
}
