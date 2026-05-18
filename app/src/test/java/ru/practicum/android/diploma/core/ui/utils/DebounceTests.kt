package ru.practicum.android.diploma.core.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val DELAY_MILLIS = 100L

private fun <T> debounceWithLog(
    scope: CoroutineScope,
    delayMillis: Long
): Pair<(T) -> Unit, List<T>> {
    val log = mutableListOf<T>()
    val debounced: (T) -> Unit = debounce(scope, delayMillis) { log.add(it) }
    return debounced to log
}

class DebounceTests {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `debounce fires only the last call after delay`() = runTest {
        val (debounced, log) = debounceWithLog<Int>(this, DELAY_MILLIS)

        debounced(1)
        advanceTimeBy(DELAY_MILLIS / 2)
        debounced(2)
        advanceTimeBy(DELAY_MILLIS / 2)
        debounced(3)
        advanceTimeBy(DELAY_MILLIS+1)

        assertEquals(listOf(3), log)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test fun `debounce does not call callback if interval too short`() = runTest {
        val (debounced, log) = debounceWithLog<Unit>(this, DELAY_MILLIS)

        debounced(Unit)
        advanceTimeBy(DELAY_MILLIS / 2)   // < delay
        debounced(Unit)
        advanceTimeBy(DELAY_MILLIS / 2)   // < delay

        assertTrue(log.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test fun `debounce works with different types`() = runTest {
        // Int
        val (debouncedInt, logInt) = debounceWithLog<Int>(this, DELAY_MILLIS)
        debouncedInt(10)
        advanceTimeBy(DELAY_MILLIS + 1)
        assertEquals(listOf(10), logInt)

        // String
        val (debouncedStr, logStr) = debounceWithLog<String>(this, DELAY_MILLIS)
        debouncedStr("hello")
        advanceTimeBy(DELAY_MILLIS + 1)
        assertEquals(listOf("hello"), logStr)
    }
}
