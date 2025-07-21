package ru.practicum.android.diploma.presentation.workplacescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterParameters

class WorkplaceFiltersViewModel(private val interactor: FiltersParametersInteractor) : ViewModel() {

    private val _countrySelectedState = MutableLiveData<FilterParameters>()
    val getSelectedCountry: LiveData<FilterParameters> = _countrySelectedState

    fun loadParameters() {
        _countrySelectedState.value = interactor.getFiltersParameters()
    }

    fun clearCountry() {
        interactor.selectCountry(null, null)
        loadParameters()
    }
}
