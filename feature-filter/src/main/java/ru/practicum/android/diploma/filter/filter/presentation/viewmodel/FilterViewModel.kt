package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

internal class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {

    private var _filterOptionsBufferLiveData: MutableLiveData<FilterSettings> = MutableLiveData<FilterSettings>()
    val filterOptionsBufferLiveData: LiveData<FilterSettings> = _filterOptionsBufferLiveData

    private var _newSettingsFilterLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val newSettingsFilterLiveData: LiveData<Boolean> = _newSettingsFilterLiveData

    fun copyDataFilterInDataFilterBuffer() {
        viewModelScope.launch {
            filterSPInteractor.copyDataFilterInDataFilterBuffer()
        }
    }

    fun copyDataFilterBufferInDataFilter() {
        viewModelScope.launch {
            filterSPInteractor.copyDataFilterBufferInDataFilter()
        }
    }

    fun getBufferDataFromSpAndCompareFilterSettings() {
        viewModelScope.launch {
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    private suspend fun getBufferDataFromSpAndCompareFilterSettingsSuspend() {
        val filterSettings = filterSPInteractor.getDataFilter()
        val filterSettingsBuffer = filterSPInteractor.getDataFilterBuffer()
        _filterOptionsBufferLiveData.postValue(filterSettingsBuffer)

        val compareFilterSettings = filterSettings == filterSettingsBuffer
        _newSettingsFilterLiveData.postValue(compareFilterSettings)
    }

    fun setSalaryInDataFilterBuffer(salaryInDataFilter: String) {
        viewModelScope.launch {
            filterSPInteractor.updateSalaryInDataFilterBuffer(salaryInDataFilter)
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun setDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary: Boolean) {
        viewModelScope.launch {
            filterSPInteractor.updateDoNotShowWithoutSalaryInDataFilterBuffer(doNotShowWithoutSalary)
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun clearPlaceInDataFilterBuffer() {
        viewModelScope.launch {
            filterSPInteractor.clearPlaceInDataFilterBuffer()
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun clearProfessionInDataFilterBuffer() {
        viewModelScope.launch {
            filterSPInteractor.clearProfessionInDataFilterBuffer()
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun clearDataFilterAll() {
        viewModelScope.launch {
            filterSPInteractor.clearDataFilterAll()
        }
    }

    fun setFilterSearch() {
        viewModelScope.launch {
            filterSPInteractor.setForceSearch()
        }
    }

    fun dropFilterSearch() {
        viewModelScope.launch {
            filterSPInteractor.dropForceSearch()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            val filterSettingsBuffer = FilterSettings.emptyFilterSettings()
            filterSPInteractor.updateDataFilterBuffer(filterSettingsBuffer)
        }
    }
}
