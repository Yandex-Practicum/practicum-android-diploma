package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor

class FilterScreenViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel()
