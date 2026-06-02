package ru.practicum.android.diploma.presentation.filtration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.presentation.filtration.action.FiltrationAction
import ru.practicum.android.diploma.presentation.filtration.effect.FiltrationEffect
import ru.practicum.android.diploma.presentation.filtration.state.FiltrationUIState

class FiltrationViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val initialState = FiltrationUIState(
        salary = null,
        onlyWithSalary = false,
        industry = null
    )
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<FiltrationUIState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<FiltrationEffect>()
    val effect: SharedFlow<FiltrationEffect> = _effects.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val filters = filtrationInteractor.getFilter()
            _state.update {
                FiltrationUIState(
                    salary = filters.salary,
                    onlyWithSalary = filters.hideWithoutSalary,
                    industry = if (filters.industryId != null) {
                        FilterIndustry(id = filters.industryId, name = filters.industryName ?: "")
                    } else {
                        null
                    }
                )
            }
        }
    }

    fun onAction(action: FiltrationAction) {
        when (action) {
            FiltrationAction.ApplyClicked -> applyFilters()
            is FiltrationAction.OnlyWithSalaryChanged -> applySalaryChecked(action.checked)
            FiltrationAction.ResetClicked -> resetFilters()
            is FiltrationAction.SalaryChanged -> applySalary(action.salary)
            FiltrationAction.SalaryCleared -> clearSalary()
            FiltrationAction.BackClicked -> navigateBack()
            FiltrationAction.IndustryClicked -> navigateIndustry()
            is FiltrationAction.IndustryChanged -> applyIndustry(action.industry)
            is FiltrationAction.CloseScreen -> saveFilters()
        }
    }

    private fun applyFilters() {
        saveFilters()
        _effects.tryEmit(
            FiltrationEffect.NavigateBack
        )
    }

    private fun saveFilters() {
        val filters = _state.value
        viewModelScope.launch(Dispatchers.IO) {
            filtrationInteractor.saveFilter(
                filterParameters = FilterParameters(
                    salary = filters.salary,
                    hideWithoutSalary = filters.onlyWithSalary,
                    industryId = filters.industry?.id,
                    industryName = filters.industry?.name
                ),
            )
        }
    }

    private fun applySalaryChecked(checked: Boolean) {
        _state.update { it.copy(onlyWithSalary = checked) }
    }

    private fun resetFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filtrationInteractor.clearFilter()
            _state.value = initialState
        }
    }

    private fun applySalary(salary: Int) {
        _state.update { it.copy(salary = salary) }
    }

    private fun applyIndustry(industry: FilterIndustry) {
        _state.update { it.copy(industry = industry) }
    }

    private fun clearSalary() {
        _state.update { it.copy(salary = null) }
    }

    private fun navigateBack() {
        _effects.tryEmit(
            FiltrationEffect.NavigateBack
        )
    }

    private fun navigateIndustry() {
        _effects.tryEmit(FiltrationEffect.OpenIndustriesScreen)
    }
}
