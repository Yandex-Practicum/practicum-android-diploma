package ru.practicum.android.diploma.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.FilterPreferences
import ru.practicum.android.diploma.ui.filter.model.FilterScreenState
import ru.practicum.android.diploma.ui.filter.model.SelectedFilters
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.Region

class FilterViewModel(
    private val filterPreferences: FilterPreferences
) : ViewModel() {

    private val state = MutableLiveData<FilterScreenState>()
    fun getState(): LiveData<FilterScreenState> = state

    private var selectedFilters = SelectedFilters(
        null,
        null,
        DEFAUlT_INDUSTRY_ID,
        DEFAULT_INDUSTRY_NAME,
        DEFAULT_SALARY,
        true
    )

    fun getFilters() {
        // Тут мы достаем сохраненные в SP фильтры
        selectedFilters = filterPreferences.loadFilters() ?:
            SelectedFilters(
                null,
                null,
                DEFAUlT_INDUSTRY_ID,
                DEFAULT_INDUSTRY_NAME,
                DEFAULT_SALARY,
                true,
            )
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun clearPlace() {
        selectedFilters =
            SelectedFilters(
                null,
                null,
                selectedFilters.industryId,
                selectedFilters.industry,
                selectedFilters.salary,
                selectedFilters.onlyWithSalary
            )
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun clearIndustry() {
        selectedFilters =
            SelectedFilters(
                selectedFilters.country,
                selectedFilters.region,
                null,
                null,
                selectedFilters.salary,
                selectedFilters.onlyWithSalary
            )
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun clearSalary() {
        selectedFilters =
            SelectedFilters(
                selectedFilters.country,
                selectedFilters.region,
                selectedFilters.industryId,
                selectedFilters.industry,
                null,
                selectedFilters.onlyWithSalary
            )
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun setShowNoSalary() {
        selectedFilters =
            SelectedFilters(
                selectedFilters.country,
                selectedFilters.region,
                selectedFilters.industryId,
                selectedFilters.industry,
                selectedFilters.salary,
                !selectedFilters.onlyWithSalary
            )
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun clearFilters() {
        filterPreferences.clearFilters()
        selectedFilters = SelectedFilters(null, null, null, null, null, false)
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun saveFilters() {
        filterPreferences.saveFilters(selectedFilters)
    }

    fun setIndustry(id: String, name: String) {
        selectedFilters = selectedFilters.copy(industryId = id, industry = name)
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun setCountry(country: Country?) {
        selectedFilters = selectedFilters.copy(country = country)
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun setRegion(region: Region?) {
        selectedFilters = selectedFilters.copy(region = region)
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    fun setSalary(salary: Int) {
        selectedFilters = selectedFilters.copy(salary = salary)
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    companion object {
        private const val DEFAUlT_INDUSTRY_ID = ""
        private const val DEFAULT_INDUSTRY_NAME = ""
        private const val DEFAULT_SALARY = 0
    }
}
