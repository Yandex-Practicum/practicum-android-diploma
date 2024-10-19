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
    private fun initFilters(): SavedFilters {
        return requestBuilderInteractor.getBufferedSavedFilters()
    }

    init {
        requestBuilderInteractor.updateBufferedSavedFilters(requestBuilderInteractor.getSavedFilters())
    }

    fun checkFilterFields() {
        val filters = initFilters()
        val area = filters.savedArea
        val areaName: String = if (!area?.name.isNullOrBlank()) {
            "${area?.parentName}, ${area?.name}"
        } else {
            area?.parentName ?: ""
        }

        baseFilterScreenState.value =
            FilterSettingsStateScreen.FilterSettings(
                areaName,
                filters.savedIndustry?.name ?: "",
                filters.savedSalary ?: "",
                filters.savedIsShowWithSalary ?: false
            )

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
