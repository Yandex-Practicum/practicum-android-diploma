package ru.practicum.android.diploma.core.data

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.data.formatters.CurrencyFormatter


class CurrencyFormatterTest() {
    val context = mockk<Context>()

    private val formatter = CurrencyFormatter(context)

    @Test fun `returns unknown`() {
        val mockedString = "Mocked String"
        every { context.getString(R.string.salary_empty) } returns mockedString
        val result = formatter.format(null, null, "USD")
        assertEquals(mockedString, result)
    }

    @Test fun `returns only from`() {
        val result = formatter.format(5000, null, "eur")
        assertEquals("от 5 000 €", result)
    }
    @Test fun `returns only to`() {
        val result = formatter.format(null, 7000, "USD")
        assertEquals("до 7 000 $", result)
    }

    @Test fun `returns from to`() {
        val result = formatter.format(3000, 6000, "RUB")
        assertEquals("от 3 000 до 6 000 ₽", result)
    }

    @Test fun `return right currency`() {
        data class TestData <A, B, C, D>(
            val from: A,
            val to: B,
            val cur: C,
            val expected: D
        )
        val testCases = listOf(
            TestData(1000, 2000, "RUR", "от 1 000 до 2 000 ₽"),
            TestData(500, null, "BYR", "от 500 Br"),
            TestData(null, 1500, "USD", "до 1 500 $"),
            TestData(1200, 2500, "eur", "от 1 200 до 2 500 €"),
            TestData(800, null, "kzt", "от 800 ₸"),
            TestData(null, 900, "uah", "до 900 ₴"),
            TestData(400, 600, "azn", "от 400 до 600 ₼"),
            TestData(700, null, "uzs", "от 700 сум"),
            TestData(null, 800, "gel", "до 800 ₾"),
            TestData(1000, 2000, "kgt", "от 1 000 до 2 000 сом")
        )

        testCases.forEach { (from, to, cur, expected) ->
            val result = formatter.format(from, to, cur)
            assertEquals(expected, result)
        }
    }

    @Test fun `returns unknown currency`() {
        val result = formatter.format(5000, 7000, "ABC")
        assertEquals("от 5 000 до 7 000 ABC", result)
    }

    @Test fun `returns value without currency`() {
        val result = formatter.format(5000, 7000, null)
        assertEquals("от 5 000 до 7 000", result)
    }
    @Test fun `returns empty currency`() {
        val result = formatter.format(3000, 6000, "")
        assertEquals("от 3 000 до 6 000", result)
    }

    @Test fun `return in right format`() {
        data class TestData <A, B, C, D>(
            val from: A,
            val to: B,
            val cur: C,
            val expected: D
        )
        val testCases = listOf(
            TestData(1, 500, "BYR", "от 1 до 500 Br"),
            TestData(1200, 20500, "eur", "от 1 200 до 20 500 €"),
            TestData(100000, null, "kzt", "от 100 000 ₸"),
            TestData(1000000, 2000000000, "RUR", "от 1 000 000 до 2 000 000 000 ₽"),
        )

        testCases.forEach { (from, to, cur, expected) ->
            val result = formatter.format(from, to, cur)
            assertEquals(expected, result)
        }
    }
}
