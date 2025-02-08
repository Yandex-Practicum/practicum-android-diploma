package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.CountryViewState

class FilterPlaceOfWorkViewModel(
    private val sharedPrefsInteractor: SharedPrefsInteractor,
    private val filterInteractor: FilterInteractor,
) : ViewModel() {
    private var filter = MutableLiveData<Filter>()

    private fun updateFilter(filter: Filter) {
        sharedPrefsInteractor.updateFilter(filter)
        loadData()
    }

    fun applyCountryIfEmptyByRegionId(filter: Filter) {
        if (filter.areaCountry == null && filter.areaCity?.id != null && filter.areaCity.parentId != null) {
            viewModelScope.launch {
                filterInteractor.getParentRegionById(filter.areaCity.parentId)
                    .collect { countryViewState ->
                        renderState(countryViewState)
                    }
            }
        }
    }

    private fun renderState(state: CountryViewState) {
        when (state) {
            is CountryViewState.CountrySelected -> updateFilter(
                Filter(
                    areaCountry = Country(
                        name = state.country.name,
                        id = state.country.id
                    )
                )
            )
        }
    }

    private fun getFilter() = sharedPrefsInteractor.getFilter()

    fun clearFilterField(field: String) {
        sharedPrefsInteractor.clearFilterField(field)
        loadData()
    }

    fun loadData() {
        filter.postValue(getFilter())
    }

    fun getFilterLiveData(): MutableLiveData<Filter> {
        return filter
    }

}
