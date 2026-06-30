package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

/** Задержка перед повторным поиском при вводе текста (ТЗ: 2000 мс). */
const val SEARCH_DEBOUNCE_MS = 2_000L

@OptIn(FlowPreview::class)
fun <T> Flow<T>.debounceSearch(): Flow<T> = debounce(SEARCH_DEBOUNCE_MS)

/**
 * Откладывает выполнение [action] на [delayMillis].
 * При повторном вызове предыдущая отложенная операция отменяется.
 */
class Debounce(
    private val scope: CoroutineScope,
    private val delayMillis: Long = SEARCH_DEBOUNCE_MS,
) {
    private var job: Job? = null

    operator fun invoke(action: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            action()
        }
    }
}
