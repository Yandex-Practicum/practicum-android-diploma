package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debounce<T>(
    private val delayMillis: Long,
    private val coroutineScope: CoroutineScope,
    private val useLastParam: Boolean,
    private val action: suspend (T) -> Unit,
) {
    private var debounceJob: Job? = null

    operator fun invoke(param: T) {
        if (useLastParam) {
            debounceJob?.cancel()
        }

        if (debounceJob?.isActive != true) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }

    fun cancel() {
        debounceJob?.cancel()
    }
}

fun <T> debounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: suspend (T) -> Unit,
): Debounce<T> = Debounce(
    delayMillis = delayMillis,
    coroutineScope = coroutineScope,
    useLastParam = useLastParam,
    action = action,
)
