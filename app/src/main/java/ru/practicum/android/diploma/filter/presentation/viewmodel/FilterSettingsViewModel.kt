package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.Filter

class FilterSettingsViewModel(private val sharedPrefsInteractor: SharedPrefsInteractor) : ViewModel() {

    fun updateFilter(filter: Filter) {
        sharedPrefsInteractor.updateFilter(filter)
    }

    fun clearFilterField(field: String) {
        sharedPrefsInteractor.clearFilterField(field)
    }

    fun getFilter() = sharedPrefsInteractor.getFilter()

    fun clearFilter() {
        sharedPrefsInteractor.clearFilter()
    }
}
