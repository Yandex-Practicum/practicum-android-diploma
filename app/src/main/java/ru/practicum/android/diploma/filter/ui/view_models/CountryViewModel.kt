package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

open class CountryViewModel @Inject constructor(
    private val useCase: GetAllCountriesUseCase,
    private val interactor: FilterInteractor,
    logger: Logger
) : AreasViewModel(logger) {

    override fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure, ::handleSuccess)
        }
    }

    fun saveCountry(country: Country) {
        log(thisName, "saveCountry(country: Country)")
        viewModelScope.launch(Dispatchers.IO) {
            interactor.refreshCountry(FILTER_KEY, country)
        }
    }
}