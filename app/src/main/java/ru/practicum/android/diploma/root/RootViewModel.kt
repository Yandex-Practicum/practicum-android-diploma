package ru.practicum.android.diploma.root


import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RootViewModel @Inject constructor(private val exampleUseCase: ExampleUseCase, logger: Logger) : BaseViewModel(logger) {

    fun doSmth(text: String) {
        exampleUseCase.printSmth(text)
        log(thisName, "doSmth()")
    }

    override fun onCleared() {
        super.onCleared()
        log(thisName, "onCleared()")
    }
}