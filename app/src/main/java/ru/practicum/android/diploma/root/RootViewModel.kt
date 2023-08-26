package ru.practicum.android.diploma.root

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RootViewModel @Inject constructor(private val exampleUseCase: ExampleUseCase, private val logger: Logger) : ViewModel() {

    fun doSmth(text: String) {
        exampleUseCase.printSmth(text)
        logger.log(thisName, "doSmth()")
    }

    override fun onCleared() {
        super.onCleared()
        logger.log(thisName, "onCleared()")
    }
}