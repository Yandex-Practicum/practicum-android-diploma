package ru.practicum.android.diploma.util

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Debounce<T>(
    private val delayMs: Long = 300L,
    private val scope: CoroutineScope
) {
    private val _debounceFlow = MutableStateFlow<T?>(null)
    val debounceFlow: StateFlow<T?> = _debounceFlow.asStateFlow()

    fun execute(value: T) {
        scope.launch {
            _debounceFlow.value = null
            delay(delayMs)
            _debounceFlow.value = value
        }
    }
}
