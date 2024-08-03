package ru.practicum.android.diploma.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.DebounceExtension.Companion.HALF_SECOND

class DebounceExtension(
    private val delayMillis: Long,
    private val action: () -> Unit
) {
    companion object {
        const val HALF_SECOND = 500L
    }
    private var debounceJob: Job? = null
    fun debounce() {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            action.invoke()
        }
    }
}

fun View.setDebouncedClickListener(
    delayMillis: Long = HALF_SECOND,
    onClick: () -> Unit
) {
    var debounceJob: Job? = null
    setOnClickListener {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            onClick()
        }
    }
}
