/**
 * Функция для задержки выполнения кода
 */
package ru.practicum.android.diploma.util.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object CoroutineUtils {

    var debounceJob: Job? = null

    fun debounce(
        scope: CoroutineScope,
        delayMillis: Long,
        action: suspend () -> Unit
    ) {
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(delayMillis)
            action()
        }
    }

}
