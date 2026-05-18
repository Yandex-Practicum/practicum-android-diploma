package ru.practicum.android.diploma.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DebounceTest {

    @Test
    fun `Debounce runs only last action after delay`() = runTest {
        var count = 0
        val debounce = Debounce(this)

        debounce { count++ }
        debounce { count++ }
        advanceUntilIdle()

        assertEquals(1, count)
    }
}
