package ru.practicum.android.diploma.common.sharedprefs.interactor

import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.common.sharedprefs.repository.SharedPrefsRepository

class SharedPrefsInteractorImpl(private val sharedPrefsRepository: SharedPrefsRepository) : SharedPrefsInteractor {

    override fun updateFilter(updatedFilter: Filter) {
        sharedPrefsRepository.updateFilter(updatedFilter)
    }

    override fun clearFilterField(field: String) {
        sharedPrefsRepository.clearFilterField(field)
    }

    override fun clearFilter() {
        sharedPrefsRepository.clearFilter()
    }

    override fun getFilter(): Filter {
        return sharedPrefsRepository.getFilter()
    }

}
