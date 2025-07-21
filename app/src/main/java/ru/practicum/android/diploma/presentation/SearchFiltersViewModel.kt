package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class SearchFiltersViewModel(
    private val interactor: FiltersParametersInteractor
) : ViewModel() {

    private val _filtersParametersState = MutableLiveData<FilterParameters>()
    val getFiltersParametersScreen: LiveData<FilterParameters> = _filtersParametersState

    fun loadParameters() {
        _filtersParametersState.value = interactor.getFiltersParameters()
    }

    fun clearWorkplace() {
        interactor.selectCountry(null, null)
//        interactor.selectRegion(null)
        loadParameters()
    }

    fun clearIndustry() {
        interactor.selectIndustry(null, null)
        loadParameters()
    }

    fun resetFilters() {
        interactor.clearFilters()
        loadParameters()
    }
}
