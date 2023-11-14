package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filter.FilterInteractor

class FilterViewModel(val interactor: FilterInteractor) : ViewModel() {

    fun setSalary(inputText: String){
        interactor.setSalary(inputText)
    }

    fun getSalary(): String {
        return interactor.getSalary()
    }
}
