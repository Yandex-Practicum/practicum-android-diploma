package ru.practicum.android.diploma.root

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.DELAY_800


abstract class DebouncingFragment(layout: Int): Fragment(layout) {
        private val delay: Long = DELAY_800
        private var job: Job? = null
        private var available = true

        fun onClickWithDebounce(action: () -> Unit) {
            if (available) {
                available = false
                job?.cancel()
                job = viewLifecycleOwner.lifecycleScope.launch {
                    action()
                    delay(delay)
                    available = true
                }
            }
        }
}

fun View.debouncingClickListener(debouncer: DebouncingFragment, action: () -> Unit) {
    setOnClickListener { debouncer.onClickWithDebounce(action) }
}