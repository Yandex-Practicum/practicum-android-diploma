package ru.practicum.android.diploma.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(
    private val coroutineScope: CoroutineScope,
    private val delay: Long = DELAY_800,
){

    private var job: Job? = null
    private var available = true

    fun onClick(action: () -> Unit) {
        if (available) {
            available = false
            job?.cancel()
            job = coroutineScope.launch {
                action()
                delay(delay)
                available = true
            }
        }
    }
}

fun View.debounceClickListener(debouncer: Debouncer, action: () -> Unit) {
    setOnClickListener { debouncer.onClick(action) }
}