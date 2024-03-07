package ru.practicum.android.diploma.filter.placeselector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.area.domain.usecase.SaveAreaUseCase
import ru.practicum.android.diploma.filter.domain.models.FilterType
import ru.practicum.android.diploma.filter.domain.usecase.GetFiltersUseCase
import ru.practicum.android.diploma.filter.placeselector.country.domain.usecase.SaveCountryFilterUseCase
import ru.practicum.android.diploma.filter.placeselector.model.PlaceSelectorState

class PlaceSelectorViewModel(
    private val getFiltersUseCase: GetFiltersUseCase,
    private val saveAreaUseCase: SaveAreaUseCase,
    private val saveCountryFilterUseCase: SaveCountryFilterUseCase
) : ViewModel() {
    private val _state = MutableLiveData(PlaceSelectorState())
    val state: LiveData<PlaceSelectorState> get() = _state

    fun init() {
        setFilters()
    }

    private fun setFilters() {
        var country: Country? = null
        var area: Area? = null
        getFiltersUseCase.execute().forEach {
            if (it is FilterType.Country) {
                country = Country(it.id, it.name)
            } else if (it is FilterType.Region) {
                area = Area(it.id, it.name, null)
            }
        }
        val currCountry = _state.value?.country
        if (currCountry != null && currCountry.id != country?.id) {
            area = null
        }
        _state.postValue(PlaceSelectorState(country, area))
    }

    fun onBtnSelectClickEvent() {
        val currCountry = state.value?.country
        val currArea = state.value?.area
        if (currCountry != null) {
            saveCountryFilterUseCase.execute(currCountry)
        }
        if (currArea != null) {
            saveAreaUseCase.execute(currArea)
        }
    }
}
