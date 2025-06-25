package ru.practicum.android.diploma.ui.filter.place.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.AreasInteractor
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.CountryState

class CountryViewModel(
    areasInteractor: AreasInteractor
) : ViewModel() {
    private val countryFilterState = MutableLiveData<CountryState>()
    val observeState: LiveData<CountryState> = countryFilterState

    private val countryList: ArrayList<Country> = arrayListOf()

    init {
        render(CountryState.Loading)
        viewModelScope.launch {
            areasInteractor.getAreas().collect { pair ->
                processAreas(pair.first, pair.second)
            }
        }
    }

    private fun processAreas(areas: List<Areas>?, error: Int?) {
        if (areas != null) {
            countryList.clear()
            fillCountryList(areas)
            render(CountryState.Content(countryList.toList()))
        }
        if (error != null) {
            render(CountryState.Error(error))
        }
    }

    private fun fillCountryList(areas: List<Areas>) {
        for (country in areas) {
            if (country.parentId == null) {
                countryList.add(
                    Country(
                        id = country.id,
                        name = country.name
                    )
                )
            }
        }
    }

    private fun render(state: CountryState) {
        countryFilterState.postValue(state)
    }
}
