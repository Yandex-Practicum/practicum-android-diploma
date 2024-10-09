package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.usecase.FilterInteractor

class FilterViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}
