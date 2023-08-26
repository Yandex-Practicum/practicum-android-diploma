package ru.practicum.android.diploma.root

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class RootViewModel @Inject constructor(private val exampleUseCase: ExampleUseCase) : ViewModel() {

    fun doSmth(text: String) {
        exampleUseCase.printSmth(text)
    }

}