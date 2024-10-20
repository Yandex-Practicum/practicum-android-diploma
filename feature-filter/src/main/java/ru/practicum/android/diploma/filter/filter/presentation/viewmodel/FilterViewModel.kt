package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.model.IndustrySetting
import ru.practicum.android.diploma.filter.filter.domain.model.PlaceSettings
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {

    private var _filterOptionsListLiveData: MutableLiveData<FilterSettings> = MutableLiveData<FilterSettings>()
    val filterOptionsListLiveData: LiveData<FilterSettings> = _filterOptionsListLiveData

    private var _filterSettingsUI: FilterSettings = FilterSettings(null, null, null, false)
    val filterSettingsUI: FilterSettings
        get() = _filterSettingsUI

    init {
        getBufferDataFromSp()
        getDataFromSp()
    }

    private fun getBufferDataFromSp() {
        _filterSettingsUI = filterSPInteractor.getDataFilter()
    }

    fun getDataFromSp() {
        val filterSettings = filterSPInteractor.getDataFilter()
        _filterOptionsListLiveData.postValue(filterSettings)
    }

    fun setSalaryInDataFilter(salaryInDataFilter: String) {
        filterSPInteractor.updateSalaryInDataFilter(salaryInDataFilter)
        getDataFromSp()
    }

    fun setDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean) {
        filterSPInteractor.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
        getDataFromSp()
    }

    fun clearPlaceInDataFilter() {
        filterSPInteractor.clearPlaceInDataFilter()
    }

    fun clearProfessionInDataFilter() {
        filterSPInteractor.clearProfessionInDataFilter()
    }

    fun clearDataFilter() {
        filterSPInteractor.clearDataFilter()
    }

    override fun onCleared() {
        super.onCleared()
        _filterSettingsUI = FilterSettings(
            PlaceSettings(
                null,
                null,
                null,
                null
            ),
            IndustrySetting(
                null,
                null
            ), null, false
        )
    }
}
