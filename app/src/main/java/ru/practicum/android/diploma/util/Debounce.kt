package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce(private val scope: CoroutineScope) {
    private var job: Job? = null
    fun <T> debounceFunction(
        delayMillis: Long,
        action: (T) -> Unit,
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }

    fun cancel() {
        job?.cancel()
    }
}
