package ru.practicum.android.diploma.utils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceExtension(private val delayMillis: Long, private val action: () -> Unit) {
    private var debounceJob: Job? = null
    fun debounce() {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            action.invoke()
        }
    }
}

fun View.setDebouncedClickListener(delayMillis: Long = NumericConstants.HALF_SECOND, onClick: () -> Unit) {
    var debounceJob: Job? = null
    setOnClickListener {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            onClick()
        }
    }
}

fun <T> debounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}
