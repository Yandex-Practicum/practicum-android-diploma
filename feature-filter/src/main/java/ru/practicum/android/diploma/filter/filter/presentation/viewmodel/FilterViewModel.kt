package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
): ViewModel() {
  init {
      val sharedPrefs = filterSPInteractor.getAll()
  }
}
