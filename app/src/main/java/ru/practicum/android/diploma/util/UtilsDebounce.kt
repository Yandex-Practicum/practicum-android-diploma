package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object UtilsDebounce {
    fun debounce(
        coroutineScope: CoroutineScope,
        job: Job?,
        function: () -> Unit,
        milliseconds: Long = 2000
    ): Job {
        job?.cancel()
        return coroutineScope.launch() {
            delay(milliseconds)
            function()
        }

    }

}
