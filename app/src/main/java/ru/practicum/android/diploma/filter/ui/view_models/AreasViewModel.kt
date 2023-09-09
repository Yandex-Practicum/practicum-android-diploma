package ru.practicum.android.diploma.filter.ui.view_models

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
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName

abstract class AreasViewModel(
    private val logger: Logger,
) : BaseViewModel(logger) {


    protected val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    open fun getData() {
//ignore
    }

    @NewResponse
    override fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NotFound -> UiState.NoData(message = R.string.no_data)
            is Failure.NetworkConnection -> UiState.Offline(message = R.string.no_internet_message)
            else -> UiState.Error(message = R.string.error)
        }
    }

    @NewResponse
    private fun handleSuccess(list: List<Any?>) {
        UiState.Content(list = list)
    }

    open fun onSearchQueryChanged(text: String) { /* ignore */
    }


}