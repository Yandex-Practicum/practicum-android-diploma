package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Функция, которая ограничивает вызов [action] не чаще, чем раз в [delayMs] миллисекунд.
 * Пример использования:
 * private val debouncedSearch = debounce<String>(500L, scope) { query ->
 *         performSearch(query)
 *}
 */

fun <T> debounce(
    delayMs: Long,
    scope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(delayMs)
            action(param)
        }
    }
}
