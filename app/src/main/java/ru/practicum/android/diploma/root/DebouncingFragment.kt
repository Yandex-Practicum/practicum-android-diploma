package ru.practicum.android.diploma.root

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.DELAY_800
import javax.inject.Inject


class Debouncer @Inject constructor() {

    private val handler = Handler(Looper.getMainLooper())
    private var available = true

    fun onClick(action: () -> Unit) {
        if (available) {
            available = false
            handler.postDelayed({
                action()
                available = true
            }, DELAY_800)
        }
    }
}

fun View.debounceClickListener(debouncer: Debouncer, action: () -> Unit) {
    setOnClickListener { debouncer.onClick(action) }
}