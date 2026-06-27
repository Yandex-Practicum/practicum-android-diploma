package ru.practicum.android.diploma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings

class FiltersViewModel(
    private val interactor: FilterSettingsInteractor
) : ViewModel() {

    private val _state = MutableLiveData<FiltersState>()
    val state: LiveData<FiltersState> = _state

    private var currentSettings: FilterSettings = FilterSettings()
    private var initialSettings: FilterSettings = FilterSettings()

    fun loadSettings() {
        _state.value = FiltersState.Loading
        val settings = interactor.getFilterSettings()
        initialSettings = settings
        currentSettings = settings
        updateState()
    }

    fun setSalary(salary: String?) {
        val salaryInt = salary?.toIntOrNull()
        if (currentSettings.expectedSalary != salaryInt) {
            currentSettings = currentSettings.copy(expectedSalary = salaryInt)
            updateState()
        }
    }

    fun setNotShowWithoutSalary(checked: Boolean) {
        if (currentSettings.notShowWithoutSalary != checked) {
            currentSettings = currentSettings.copy(notShowWithoutSalary = checked)
            updateState()
        }
    }

    fun applyFilters() {
        interactor.saveFilterSettings(currentSettings)
        initialSettings = currentSettings
        updateState()
    }

    fun resetFilters() {
        currentSettings = FilterSettings()
        updateState()
    }

    fun clearWorkPlace() {
        currentSettings = currentSettings.copy(
            countryId = null,
            countryName = null,
            regionId = null,
            regionName = null
        )
        updateState()
    }

    fun clearIndustry() {
        currentSettings = currentSettings.copy(
            industryId = null,
            industryName = null
        )
        updateState()
    }

    private fun updateState() {
        val canApply = currentSettings != initialSettings
        val canReset = currentSettings != FilterSettings()
        _state.value = FiltersState.Content(
            settings = currentSettings,
            canApply = canApply,
            canReset = canReset
        )
    }
}
