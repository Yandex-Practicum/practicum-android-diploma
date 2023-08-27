package ru.practicum.android.diploma.root

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.Logger

abstract class BaseViewModel(private val logger: Logger) : ViewModel() {
    fun log(name: String, text: String) {
        logger.log(name, text)
    }
}