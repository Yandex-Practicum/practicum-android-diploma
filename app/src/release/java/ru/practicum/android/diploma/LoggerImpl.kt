package ru.practicum.android.diploma

import javax.inject.Inject

class LoggerImpl @Inject constructor(): Logger {
    override fun log(className: String, method: String) { /* ignore */ }
}