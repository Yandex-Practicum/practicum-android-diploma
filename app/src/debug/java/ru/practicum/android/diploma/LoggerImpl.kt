package ru.practicum.android.diploma

import android.util.Log
import ru.practicum.android.diploma.di.ApplicationScope
import javax.inject.Inject


@ApplicationScope
class LoggerImpl @Inject constructor(): Logger {
    override fun log(className: String, method: String) {
        Log.d("debug", "$className -> $method")
    }
}