package ru.practicum.android.diploma

import android.util.Log
import javax.inject.Inject

class LoggerImpl @Inject constructor(): Logger {
    override fun log(className: String, method: String) {
        Log.d("debug", "$className -> $method")
    }
}