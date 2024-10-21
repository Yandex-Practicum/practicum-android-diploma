package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {

    private var _filterOptionsListLiveData: MutableLiveData<FilterSettings> = MutableLiveData<FilterSettings>()
    val filterOptionsListLiveData: LiveData<FilterSettings> = _filterOptionsListLiveData

    private var _newSettingsFilterLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val newSettingsFilterLiveData: LiveData<Boolean> = _newSettingsFilterLiveData

    init {
        copyDataFilterInDataFilterBuffer()
    }

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
            val filterSettings = filterSPInteractor.getDataFilter()
            val filterSettingsBuffer = filterSPInteractor.getDataFilterBuffer()
            _filterOptionsListLiveData.postValue(filterSettingsBuffer)

            val compareFilterSettings = filterSettings == filterSettingsBuffer
            _newSettingsFilterLiveData.postValue(compareFilterSettings)
        }
    }

    private suspend fun getBufferDataFromSpAndCompareFilterSettingsSuspend() {
        val filterSettings = filterSPInteractor.getDataFilter()
        val filterSettingsBuffer = filterSPInteractor.getDataFilterBuffer()
        _filterOptionsListLiveData.postValue(filterSettingsBuffer)

        val compareFilterSettings = filterSettings == filterSettingsBuffer
        _newSettingsFilterLiveData.postValue(compareFilterSettings)
    }

    fun setSalaryInDataFilter(salaryInDataFilter: String) {
        viewModelScope.launch {
            filterSPInteractor.updateSalaryInDataFilter(salaryInDataFilter)
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun setDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean) {
        viewModelScope.launch {
            filterSPInteractor.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun clearPlaceInDataFilter() {
        viewModelScope.launch {
            filterSPInteractor.clearPlaceInDataFilter()
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun clearProfessionInDataFilter() {
        viewModelScope.launch {
            filterSPInteractor.clearProfessionInDataFilter()
            getBufferDataFromSpAndCompareFilterSettingsSuspend()
        }
    }

    fun clearDataFilter() {
        viewModelScope.launch {
            filterSPInteractor.clearDataFilter()
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
