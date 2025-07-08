package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(private val scope: CoroutineScope,
                private val debounceDelay: Long) {
    private var job: Job? = null

    fun submit(action: () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(debounceDelay)
            action()
        }
    }

    fun cancel() {
        job?.cancel()
        job = null
    }
}
