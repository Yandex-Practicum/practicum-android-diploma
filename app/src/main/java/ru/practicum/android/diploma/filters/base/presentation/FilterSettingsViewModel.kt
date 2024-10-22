package ru.practicum.android.diploma.filters.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class FilterSettingsViewModel(
    private val requestBuilderInteractor: RequestBuilderInteractor,
) : ViewModel() {

    private val baseFilterScreenState: MutableLiveData<FilterSettingsStateScreen> = MutableLiveData()
    val getBaseFiltersScreenState: LiveData<FilterSettingsStateScreen> = baseFilterScreenState
    private var currentFilters: SavedFilters? = null
    private var savedFilters: SavedFilters = requestBuilderInteractor.getSavedFilters()

    init {
        requestBuilderInteractor.updateBufferedSavedFilters(requestBuilderInteractor.getSavedFilters())
    }

    fun checkFilterFields() {
        currentFilters = requestBuilderInteractor.getBufferedSavedFilters()
        val area = currentFilters?.savedArea
        val areaName: String = if (!area?.name.isNullOrBlank()) {
            "${area?.parentName}, ${area?.name}"
        } else {
            area?.parentName ?: ""
        }

        baseFilterScreenState.value =
            FilterSettingsStateScreen.FilterSettings(
                areaName,
                currentFilters?.savedIndustry?.name ?: "",
                currentFilters?.savedSalary ?: "",
                currentFilters?.savedIsShowWithSalary ?: false
            )
    }

    fun checkFilter(): Boolean {
        return !(
            currentFilters?.savedArea == null &&
                currentFilters?.savedSalary.isNullOrEmpty() &&
                currentFilters?.savedIndustry == null &&
                currentFilters?.savedIsShowWithSalary == false
            )
    }

    fun compareFilters(): Boolean = savedFilters == currentFilters

    fun clearCurrentArea() {
        currentFilters = currentFilters?.copy(savedArea = null)
    }

    fun clearCurrentIndustry() {
        currentFilters = currentFilters?.copy(savedIndustry = null)
    }

    fun updateSalaryCheckbox(isChecked: Boolean) {
        currentFilters = currentFilters?.copy(savedIsShowWithSalary = isChecked)
    }

    fun updateSalary(newSalary: String) {
        currentFilters = currentFilters?.copy(savedSalary = newSalary)
    }

    fun clearFilter() {
        requestBuilderInteractor.clearAllFilters()
        currentFilters = requestBuilderInteractor.getBufferedSavedFilters()
    }

    fun cleanCashArea() {
        requestBuilderInteractor.updateBufferedSavedFilters(
            requestBuilderInteractor.getBufferedSavedFilters().copy(savedArea = null)
        )
    }

    fun clearIndustry() {
        requestBuilderInteractor.updateBufferedSavedFilters(
            requestBuilderInteractor.getBufferedSavedFilters().copy(savedIndustry = null)
        )
    }

    fun setSalary(salary: String) {
        requestBuilderInteractor.setSalary(salary)
    }

    fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        requestBuilderInteractor.setIsShowWithSalary(isShowWithSalary)
    }

    fun saveFilters() {
        requestBuilderInteractor.saveFiltersToShared()
    }

}
