package ru.practicum.android.diploma.domain

import android.util.Log
import com.bumptech.glide.util.Util

const val DEBUG = true

fun debugLog(tag: String, event: () -> String) {
    if (DEBUG) {
        Log.d(tag, event.invoke())
    }
}
