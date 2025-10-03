package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class SearchDebounce<T>(
    private val delayMs: Long = 2000L,
    private val scope: CoroutineScope
) {
    private val _debounceFlow = MutableStateFlow<T?>(null)
    val debounceFlow: StateFlow<T?> = _debounceFlow.asStateFlow()
    private var job:Job? = null

    fun execute(value: T) {
        job?.cancel()
        job = scope.launch {
            delay(delayMs)
            _debounceFlow.value = value
        }
    }
}
