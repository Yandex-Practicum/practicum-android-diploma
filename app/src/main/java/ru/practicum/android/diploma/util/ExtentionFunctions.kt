package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DELAY_800 = 800L
const val DELAY_1500 = 1500L

val <T> T.thisName: String
    get() = this!!::class.simpleName ?: "Unknown class"

fun <T> delayedAction(
    delayMillis: Long = DELAY_1500,
    coroutineScope: CoroutineScope,
    deferredUsing: Boolean = false,
    action: (T) -> Unit,
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (deferredUsing) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || deferredUsing) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}