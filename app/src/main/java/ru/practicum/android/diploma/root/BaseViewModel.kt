package ru.practicum.android.diploma.root

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName

abstract class BaseViewModel(private val logger: Logger) : ViewModel() {
    open fun handleFailure(failure: Failure) {
        Log.e(thisName, "FAILURE: ${failure.code} ", )
    }

    fun log(name: String, text: String) {
        logger.log(name, text)
    }
}