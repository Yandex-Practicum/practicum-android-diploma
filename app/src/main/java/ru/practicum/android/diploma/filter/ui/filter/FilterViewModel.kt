package ru.practicum.android.diploma.filter.ui.filter

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor

class FilterViewModel(
    private val filterInteractor: FilterInteractor,
) : ViewModel()
