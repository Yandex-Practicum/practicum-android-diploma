package ru.practicum.android.diploma.presentation.filtration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.presentation.filtration.state.FiltrationUIState

class FiltrationViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val initialState = FiltrationUIState(
        salary = null,
        onlyWithSalary = false,
        industry = null,
    )
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<FiltrationUIState> = _state.asStateFlow()

    init {
        loadFilters()
    }

    fun loadFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            val filters = filtrationInteractor.getFilter()
            _state.update {
                FiltrationUIState(
                    salary = filters.salary,
                    onlyWithSalary = filters.hideWithoutSalary,
                    industry = filters.industryId?.let { id ->
                        FilterIndustry(id = id, name = filters.industryName.orEmpty())
                    },
                )
            }
        }
    }

    fun onSalaryChanged(text: String) {
        val digits = text.filter { it.isDigit() }
        when {
            digits.isEmpty() -> onSalaryCleared()
            else -> digits.toIntOrNull()?.let { newSalary -> _state.update { it.copy(salary = newSalary) } }
        }
    }

    fun onSalaryChanged(salary: Int) {
        _state.update { it.copy(salary = salary) }
    }

    fun onSalaryCleared() {
        _state.update { it.copy(salary = null) }
    }

    fun onOnlyWithSalaryChanged(checked: Boolean) {
        _state.update { it.copy(onlyWithSalary = checked) }
    }

    fun clearIndustry() {
        _state.update { it.copy(industry = null) }
    }

    fun onIndustryChanged(industry: FilterIndustry) {
        _state.update { it.copy(industry = industry) }
    }

    fun saveFilters() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                filtrationInteractor.saveFilter(
                    filterParameters = FilterParameters(
                        salary = _state.value.salary,
                        hideWithoutSalary = _state.value.onlyWithSalary,
                        industryId = _state.value.industry?.id,
                        industryName = _state.value.industry?.name,
                    ),
                )
            }
        }
    }

    fun resetFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            filtrationInteractor.clearFilter()
            _state.value = initialState
        }
    }

    private suspend fun persistFilters(filters: FiltrationUIState) {
        withContext(Dispatchers.IO) {
            filtrationInteractor.saveFilter(
                filterParameters = FilterParameters(
                    salary = filters.salary,
                    hideWithoutSalary = filters.onlyWithSalary,
                    industryId = filters.industry?.id,
                    industryName = filters.industry?.name,
                ),
            )
        }
    }
}
