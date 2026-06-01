package ru.practicum.android.diploma.presentation.filtration.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.presentation.filtration.action.FiltrationAction
import ru.practicum.android.diploma.presentation.filtration.effect.Filters
import ru.practicum.android.diploma.presentation.filtration.effect.FiltrationEffect
import ru.practicum.android.diploma.presentation.filtration.state.FiltrationUIState

class FiltrationViewModel : ViewModel() {
    private val initialState = FiltrationUIState(
        salary = null,
        onlyWithSalary = false,
        industry = null
    )
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<FiltrationUIState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<FiltrationEffect>()
    val effect: SharedFlow<FiltrationEffect> = _effects.asSharedFlow()

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
        }
    }

    private fun applyFilters() {
        val filters = _state.value
        _effects.tryEmit(
            FiltrationEffect.NavigateBack(
                Filters(
                    salary = filters.salary,
                    onlyWithSalary = filters.onlyWithSalary,
                    industry = filters.industry,
                )
            )
        )
    }

    private fun applySalaryChecked(checked: Boolean) {
        _state.update { it.copy(onlyWithSalary = checked) }
    }

    private fun resetFilters() {
        _state.value = initialState
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
            FiltrationEffect.NavigateBack(
                Filters(
                    salary = null,
                    onlyWithSalary = false,
                    industry = null
                )
            )
        )
    }

    private fun navigateIndustry() {
        _effects.tryEmit(FiltrationEffect.OpenIndustriesScreen)
    }
}
