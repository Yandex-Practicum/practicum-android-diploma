package ru.practicum.android.diploma.domain.filter.impl

import ru.practicum.android.diploma.data.filter.FilterSharedPreferencesRepository
import ru.practicum.android.diploma.domain.filter.FilterSharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterSharedPreferencesInteractorImpl(private val repository: FilterSharedPreferencesRepository) :
    FilterSharedPreferencesInteractor {

    override fun getFilterSharedPrefs(): Filter? {
        return repository.getFilterSharedPrefs()
    }

    override fun setFilterSharedPrefs(filter: Filter) {
        return repository.setFilterSharedPrefs(filter)
    }

    override fun deleteFilterSharedPrefs() {
        return repository.clearFilterSharedPrefs()
    }
    override fun clearRegions(newFilter: Filter) {
        repository.clearRegions(newFilter)
    }
}
