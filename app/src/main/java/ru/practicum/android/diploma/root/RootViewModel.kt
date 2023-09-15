package ru.practicum.android.diploma.root

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RootViewModel @Inject constructor(logger: Logger) : BaseViewModel(logger) {

    override fun onCleared() {
        super.onCleared()
        log(thisName, "onCleared()")
    }
}