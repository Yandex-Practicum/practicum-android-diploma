package ru.practicum.android.diploma.search.presenter.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
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

    private var initialIndustry: Industry? = null
    private var initialSalary: String? = null
    private var initialNoSalaryOnly: Boolean = false

    val hasAnyActiveFilters = combine(
        _selectedIndustry,
        _expectedSalary,
        _noSalaryOnly
    ) { industry, salary, noSalary ->
        industry != null || !salary.isNullOrBlank() || noSalary
    }

    val areFiltersChanged = combine(
        _selectedIndustry,
        _expectedSalary,
        _noSalaryOnly
    ) { currentIndustry, currentSalary, currentNoSalary ->
        currentIndustry != initialIndustry ||
            currentSalary ?: "" != initialSalary ?: "" ||
            currentNoSalary != initialNoSalaryOnly
    }

    init {
        loadSavedFilters()
    }

    fun updateIndustry(industry: Industry?) {
        _selectedIndustry.value = industry
    }

    fun updateSalary(salary: String?) {
        _expectedSalary.value = salary?.trim()
    }

    fun updateNoSalaryOnly(isChecked: Boolean) {
        _noSalaryOnly.value = isChecked
    }

    fun applyFilters() {
        saveFilters()
        loadSavedFilters()
    }

    fun clearFilters() {
        updateIndustry(null)
        updateSalary(null)
        updateNoSalaryOnly(false)
        filtersInteractor.clearSavedFilters()

        initialIndustry = null
        initialSalary = null
        initialNoSalaryOnly = false
    }

    private fun loadSavedFilters() {
        viewModelScope.launch {
            val (industry, salary, onlyWithSalary) = filtersInteractor.getSavedFilters()
            _selectedIndustry.value = industry
            _expectedSalary.value = salary
            _noSalaryOnly.value = onlyWithSalary

            initialIndustry = industry
            initialSalary = salary
            initialNoSalaryOnly = onlyWithSalary
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
}
