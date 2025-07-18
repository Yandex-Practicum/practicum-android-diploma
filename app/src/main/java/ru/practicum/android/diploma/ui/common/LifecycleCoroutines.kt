package ru.practicum.android.diploma.ui.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

inline fun LifecycleOwner.launchAndRepeatOnLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

inline fun Fragment.launchAndRepeatOnLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.launchAndRepeatOnLifecycle(minActiveState, block)
}

class CommandChannel<T> {
    private val channel = Channel<T>(Channel.UNLIMITED)

    fun receiveAsFlow() = channel.receiveAsFlow()

    suspend fun send(command: T) {
        withContext(Dispatchers.Main.immediate) {
            channel.send(command)
        }
    }
}
