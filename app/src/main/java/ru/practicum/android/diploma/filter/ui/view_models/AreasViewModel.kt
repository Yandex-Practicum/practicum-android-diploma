package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetAllCountriesUseCase
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse.*
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.Failure.*
import ru.practicum.android.diploma.util.thisName

abstract class AreasViewModel(
    private val logger: Logger,
    private val countryUseCase: GetAllCountriesUseCase,
) : ViewModel() {


    protected val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    open fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            countryUseCase.getAllCountries().fold(::handleFailure, ::handleSuccess)
        }
    }

    @NewResponse
    protected open fun handleFailure(failure: Failure) {
        when (failure) {
            is NotFound          -> UiState.NoData(message = R.string.no_data)
            is NetworkConnection -> UiState.Offline(message = R.string.no_internet_message)
            else                 -> UiState.Error(message = R.string.error)
        }
    }

    @NewResponse
    private fun handleSuccess(list: List<Any?>) {
        UiState.Content(list = list)
    }

    open fun onSearchQueryChanged(text: String) { /* ignore */ }

    fun log(name: String, text: String) = logger.log(name, text)
//==============================================================
//    @NewResponse
//    private fun getData() {
//    //searchJob?.cancel()
//    _uiState.value = SearchScreenState.Loading
//    searchJob = viewModelScope.launch(Dispatchers.IO) {
//        searchVacanciesUseCase.searchVacancies(query)
//            .fold(::handleFailure, ::handleSuccess)
//    }
//}
//

}