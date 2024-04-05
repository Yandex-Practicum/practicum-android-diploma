package ru.practicum.android.diploma.domain

import android.util.Log

const val DEBUG = true

fun debugLog(tag: String, event: () -> String) {
    if (DEBUG) {
        Log.d(tag, event.invoke())
    }
}
