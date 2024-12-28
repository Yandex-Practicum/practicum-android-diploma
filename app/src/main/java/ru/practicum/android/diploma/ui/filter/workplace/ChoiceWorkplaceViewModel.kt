package ru.practicum.android.diploma.ui.filter.workplace

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filter.FilterSharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter

class ChoiceWorkplaceViewModel(
    private val interactor: FilterSharedPreferencesInteractor
) : ViewModel() {

    fun setFilter(filter: Filter) {
        interactor.setFilterSharedPrefs(filter)
    }

    fun clearRegion(filter: Filter) {
        interactor.clearRegions(filter)
    }
}
