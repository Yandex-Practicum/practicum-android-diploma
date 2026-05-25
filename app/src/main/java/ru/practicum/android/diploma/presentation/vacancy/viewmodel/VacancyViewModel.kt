package ru.practicum.android.diploma.presentation.vacancy.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyActionInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse
import ru.practicum.android.diploma.domain.models.VacancyAction
import ru.practicum.android.diploma.presentation.vacancy.state.VacancyDetailsUiState
import ru.practicum.android.diploma.ui.vacancy.fragment.VacancyFragmentArgs

class VacancyViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val vacancyDetailInteractor: VacancyDetailInteractor,
    private val vacancyActionInteractor: VacancyActionInteractor,
) : ViewModel() {
    private val vacancyId = VacancyFragmentArgs.fromSavedStateHandle(savedStateHandle).vacancyId
    private val _state = MutableStateFlow<VacancyDetailsUiState>(VacancyDetailsUiState.Loading)
    val state: StateFlow<VacancyDetailsUiState> = _state.asStateFlow()

    fun loadVacancy() {
        viewModelScope.launch {
            _state.value = VacancyDetailsUiState.Loading
            when (val result = vacancyDetailInteractor.getVacancyDetails(vacancyId)) {
                GetVacancyDetailsResponse.Error -> _state.value = VacancyDetailsUiState.Error
                GetVacancyDetailsResponse.NotFound -> _state.value = VacancyDetailsUiState.NotFound
                GetVacancyDetailsResponse.ServerError -> _state.value = VacancyDetailsUiState.ServerError
                is GetVacancyDetailsResponse.Success -> _state.value = VacancyDetailsUiState.Content(result.result)
            }
        }
    }

    fun onAction(action: VacancyAction) {
        viewModelScope.launch { vacancyActionInteractor.handleAction(action) }
    }
}
