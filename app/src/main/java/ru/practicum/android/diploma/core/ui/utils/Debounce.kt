package ru.practicum.android.diploma.core.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    scope: CoroutineScope,
    delayMillis: Long,
    onDebounced: (T) -> Unit
): (T) -> Unit {
    var job: Job? = null

    return { param: T ->
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            onDebounced(param)
        }
    }
}

