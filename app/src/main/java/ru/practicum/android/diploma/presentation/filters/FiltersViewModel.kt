package ru.practicum.android.diploma.presentation.filters

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

class FiltersViewModel(
    private val filterInteractor: FiltersInteractor,
): ViewModel() {
    fun getPrefs(): FiltersSettings = filterInteractor.getPrefs()
    fun savePrefs(settings: FiltersSettings) = filterInteractor.savePrefs(settings)
    fun clearPrefs() = filterInteractor.clearPrefs()
}
