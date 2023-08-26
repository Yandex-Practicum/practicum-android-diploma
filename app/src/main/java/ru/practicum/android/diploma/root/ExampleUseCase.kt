package ru.practicum.android.diploma.root

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class ExampleUseCase @Inject constructor(private val logger: Logger) {

    fun printSmth(text: String){
        logger.log(thisName, "printSmth(text: String) -> $text")
    }
}