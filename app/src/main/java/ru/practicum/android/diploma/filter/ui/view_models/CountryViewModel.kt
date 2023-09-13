package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import javax.inject.Inject

open class CountryViewModel @Inject constructor(
    private val useCase: GetAllCountriesUseCase,
    logger: Logger
) : AreasViewModel(logger) {

    override fun getData(data: SelectedFilter) {
        selectedFilter = data
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure, ::handleSuccess)
        }
    }

    fun refreshCountry(country: Country) {
        if (country != selectedFilter.country) {
            selectedFilter = selectedFilter.copy(country = country, region = null)
        }
    }

}