package ru.practicum.android.diploma.ui.filter.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.practicum.android.diploma.domain.filter.FilterSharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterSettingsViewModel(
    application: Application,
    private val interactor: FilterSharedPreferencesInteractor
) : AndroidViewModel(application) {

    val currentFilter: Filter by lazy {
        interactor.getFilterSharedPrefs() ?: Filter()
    }

    fun setSavedFilterToUi(): Filter {
        return currentFilter
    }

    fun saveFilterFromUi(filter: Filter) {
        interactor.setFilterSharedPrefs(filter)
    }

    fun clearFilters() {
        interactor.deleteFilterSharedPrefs()
    }

}
