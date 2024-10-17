package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        viewModelScope.launch(Dispatchers.IO) {
            _filterSettingsUI = filterSPInteractor.getDataFilter()
        }
    }

    fun getDataFromSp() {
        viewModelScope.launch(Dispatchers.IO) {
            val filterSettings = filterSPInteractor.getDataFilter()
            _filterOptionsListLiveData.postValue(filterSettings)
        }
    }

    fun setSalaryInDataFilter(salaryInDataFilter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.updateSalaryInDataFilter(salaryInDataFilter)
            getDataFromSp()
        }
    }

    fun setDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
            getDataFromSp()
        }
    }

    fun clearPlaceInDataFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearPlaceInDataFilter()
        }
    }
    fun clearProfessionInDataFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearProfessionInDataFilter()
        }
    }

    fun clearDataFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearDataFilter()
        }
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
            ), null, false)
    }
}
