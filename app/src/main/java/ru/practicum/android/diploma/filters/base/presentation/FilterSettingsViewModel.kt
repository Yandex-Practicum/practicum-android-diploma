package ru.practicum.android.diploma.filters.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class FilterSettingsViewModel(
    private val requestBuilderInteractor: RequestBuilderInteractor,
    private val areaCashInteractor: AreaCashInteractor
) : ViewModel() {

    private val baseFilterScreenState: MutableLiveData<FilterSettingsStateScreen> = MutableLiveData()
    val getBaseFiltersScreenState: LiveData<FilterSettingsStateScreen> = baseFilterScreenState
    private fun initFilters(): SavedFilters {
        return requestBuilderInteractor.getSavedFilters()
    }
    init {
        areaCashInteractor.resetCashArea()
    }

    fun checkFilterFields() {
        val filters = initFilters()
        val area = areaCashInteractor.getCashArea()
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
        areaCashInteractor.cleanCashArea()
    }

    fun clearIndustry() {
        requestBuilderInteractor.cleanIndustry()
    }

    fun setSalary(salary: String) {
        requestBuilderInteractor.setSalary(salary)
    }
    fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        requestBuilderInteractor.setIsShowWithSalary(isShowWithSalary)
    }
    fun saveArea() {
        areaCashInteractor.saveArea()
    }

}
