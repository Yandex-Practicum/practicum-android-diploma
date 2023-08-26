package ru.practicum.android.diploma.root

import javax.inject.Inject

class RootViewModel @Inject constructor(private val exampleUseCase: ExampleUseCase) {

    fun doSmth(text: String){
        exampleUseCase.printSmth(text)
    }

}