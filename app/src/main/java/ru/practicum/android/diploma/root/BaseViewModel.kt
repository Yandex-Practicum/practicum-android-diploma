package ru.practicum.android.diploma.root


import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName

abstract class BaseViewModel(
    private val logger: Logger
) : ViewModel() {

    protected open fun handleFailure(failure: Failure) {
        logger.log(thisName, "handleFailure: ${failure.code} ", )
    }

    fun log(name: String, text: String) {
        logger.log(name, text)
    }
}