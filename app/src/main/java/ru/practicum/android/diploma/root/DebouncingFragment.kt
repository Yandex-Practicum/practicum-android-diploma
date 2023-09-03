package ru.practicum.android.diploma.root

import android.os.Handler
import android.os.Looper
import android.view.View
import ru.practicum.android.diploma.util.DELAY_800_MILLIS
import javax.inject.Inject


class Debouncer @Inject constructor() {

    private val handler = Handler(Looper.getMainLooper())
    private var available = true

    fun onClick(action: () -> Unit) {
        if (available) {
            available = false
            action()
            handler.postDelayed({
                available = true
            }, DELAY_800_MILLIS)
        }
    }
}

fun View.debounceClickListener(debouncer: Debouncer, action: () -> Unit) {
    setOnClickListener { debouncer.onClick(action) }
}