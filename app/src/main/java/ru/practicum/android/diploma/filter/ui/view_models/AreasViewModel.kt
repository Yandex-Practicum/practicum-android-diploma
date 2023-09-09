package ru.practicum.android.diploma.filter.ui.view_models


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.Failure.*

abstract class AreasViewModel(
    logger: Logger,
) : BaseViewModel(logger) {


    protected val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    open fun getData() { /* ignore */}

    override fun handleFailure(failure: Failure) {
        when (failure) {
            is NotFound -> UiState.NoData(message = R.string.no_data)
            is Offline  -> UiState.Offline(message = R.string.no_internet_message)
            else        -> UiState.Error(message = R.string.error)
        }
    }
    protected fun handleSuccess(list: List<Any?>) {
        UiState.Content(list = list)
    }

    open fun onSearchQueryChanged(text: String) { /* ignore */ }
}