package ru.practicum.android.diploma.search.presenter.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.FiltersInteractor
import ru.practicum.android.diploma.search.domain.model.Industry

class FiltersViewModel(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private val _selectedIndustry = MutableStateFlow<Industry?>(null)
    val selectedIndustry = _selectedIndustry.asStateFlow()

    private val _expectedSalary = MutableStateFlow<String?>(null)
    val expectedSalary = _expectedSalary.asStateFlow()

    private val _noSalaryOnly = MutableStateFlow(false)
    val noSalaryOnly = _noSalaryOnly.asStateFlow()

    private val _applyFiltersEvent = MutableSharedFlow<Unit>()
    val applyFiltersEvent = _applyFiltersEvent.asSharedFlow()

    val hasActiveFilters = combine(
        _selectedIndustry,
        _expectedSalary,
        _noSalaryOnly
    ) { industry, salary, noSalary ->
        industry != null || !salary.isNullOrBlank() || noSalary
    }

    fun updateIndustry(industry: Industry?) {
        _selectedIndustry.value = industry
    }

    init {
        loadSavedFilters()
    }

    fun updateSalary(salary: String?) {
        _expectedSalary.value = salary
    }

    fun updateNoSalaryOnly(isChecked: Boolean) {
        _noSalaryOnly.value = isChecked
    }

    fun applyFilters() {
        viewModelScope.launch {
            _applyFiltersEvent.emit(Unit)
        }
    }

    fun clearFilters() {
        updateIndustry(null)
        updateSalary(null)
        updateNoSalaryOnly(false)
        filtersInteractor.clearSavedFilters()
    }

    private fun loadSavedFilters() {
        viewModelScope.launch {
            val (industry, salary, onlyWithSalary) = filtersInteractor.getSavedFilters()
            _selectedIndustry.value = industry
            _expectedSalary.value = salary
            _noSalaryOnly.value = onlyWithSalary
        }
    }

    fun saveFilters() {
        viewModelScope.launch {
            filtersInteractor.saveFilters(
                industry = _selectedIndustry.value,
                salary = _expectedSalary.value,
                onlyWithSalary = _noSalaryOnly.value
            )
        }
    }

    fun getFiltersAsMap(): Map<String, String> {
        val filters = mutableMapOf<String, String>()
        _selectedIndustry.value?.id?.let { filters["industry"] = it }
        _expectedSalary.value?.let { if (it.isNotBlank()) filters["salary"] = it }
        if (_noSalaryOnly.value) {
            filters["only_with_salary"] = "true"
        }
        return filters
    }
}
