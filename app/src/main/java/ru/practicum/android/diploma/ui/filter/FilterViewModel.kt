package ru.practicum.android.diploma.ui.filter

import android.content.*
import androidx.core.view.*
import androidx.lifecycle.*
import ru.practicum.android.diploma.*
import ru.practicum.android.diploma.databinding.*
import ru.practicum.android.diploma.domain.api.*
import ru.practicum.android.diploma.ui.filter.model.*
import ru.practicum.android.diploma.ui.filter.place.models.*

class FilterViewModel(
    private val filterPreferences: FilterPreferencesInteractor
) : ViewModel() {

    private val state = MutableLiveData<FilterScreenState>()
    fun getState(): LiveData<FilterScreenState> = state

    private var selectedFilters = DEFAULT_FILTERS

    fun getFilters() {
        // Тут мы достаем сохраненные в SP фильтры
        selectedFilters = filterPreferences.loadFilters() ?: DEFAULT_FILTERS
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

    fun setSalary(salary: Int?) {
        selectedFilters = selectedFilters.copy(salary = salary)
        state.postValue(FilterScreenState.CONTENT(selectedFilters))
    }

    companion object {
        private const val DEFAUlT_INDUSTRY_ID = ""
        private const val DEFAULT_INDUSTRY_NAME = ""

        private val DEFAULT_FILTERS = SelectedFilters(
            null,
            null,
            DEFAUlT_INDUSTRY_ID,
            DEFAULT_INDUSTRY_NAME,
            null,
            false
        )
    }

    fun screenInit(binding: FragmentFilterBinding, context: Context) {
        binding.topbar.apply {
            btnFirst.setImageResource(R.drawable.arrow_back_24px)
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = context.getString(R.string.filter_settings)
        }
        binding.includedPlace.apply {
            itemTextTop.text = context.getString(R.string.place)
            itemTextTop.isVisible = false
            itemText.hint = itemTextTop.text
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
        }
        binding.includedIndustry.apply {
            itemTextTop.text = context.getString(R.string.industry)
            itemTextTop.isVisible = false
            itemText.hint = itemTextTop.text
            itemIcon.setImageResource(R.drawable.arrow_forward_24px)
        }
        binding.includedBtnSet.root.isVisible = false
        binding.includedBtnCancel.root.isVisible = false
    }
}
