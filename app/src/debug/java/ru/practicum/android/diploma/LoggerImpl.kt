package ru.practicum.android.diploma

import android.util.Log

class LoggerImpl: Logger {
    override fun log(className: String, method: String) {
        Log.d("debug", "$className -> $method")
    }
}