package ru.practicum.android.diploma.ui.filter.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.practicum.android.diploma.domain.filter.FilterSharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterSettingsViewModel(
    application: Application,
    private val interactor: FilterSharedPreferencesInteractor
) : AndroidViewModel(application) {

    private val _counterFilter = MutableLiveData<FilterSettingsState>()
    val counterFilter: LiveData<FilterSettingsState> get() = _counterFilter

    fun currentFilter() {
        processResult(interactor.getFilterSharedPrefs())
    }

    private fun processResult(result: Filter?) {
        if (result != null) {
            renderState(FilterSettingsState.FilterSettings(result))
        } else {
            renderState(FilterSettingsState.Empty)
        }
    }

    fun saveFilterFromUi(filter: Filter) {
        interactor.setFilterSharedPrefs(filter)
    }

    fun clearFilters() {
        interactor.deleteFilterSharedPrefs()
        renderState(FilterSettingsState.Empty)
    }

    private fun renderState(state: FilterSettingsState) {
        _counterFilter.postValue(state)
    }
}
