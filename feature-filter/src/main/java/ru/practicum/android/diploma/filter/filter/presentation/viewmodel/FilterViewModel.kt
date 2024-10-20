package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import android.util.Log
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
            Log.e("getBufferDataFromSpAndCompareFilterSettings","filterSettings $filterSettings")
            Log.e("getBufferDataFromSpAndCompareFilterSettings","filterSettingsBuffer $filterSettingsBuffer")
            _filterOptionsListLiveData.postValue(filterSettingsBuffer)

            val compareFilterSettings = filterSettings == filterSettingsBuffer
            Log.e("getBufferDataFromSpAndCompareFilterSettings","compareFilterSettings $compareFilterSettings")
            _newSettingsFilterLiveData.postValue(compareFilterSettings)
        }
    }

    fun compareFilterSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            val filterSettings = filterSPInteractor.getDataFilter()
            val filterSettingsBuffer = filterSPInteractor.getDataFilterBuffer()
            val compareFilterSettings = filterSettings == filterSettingsBuffer
            _newSettingsFilterLiveData.postValue(compareFilterSettings)
        }
    }

    fun setSalaryInDataFilter(salaryInDataFilter: String) {
        viewModelScope.launch {
            filterSPInteractor.updateSalaryInDataFilter(salaryInDataFilter)
//            getBufferDataFromSpAndCompareFilterSettings()
        }
    }

    fun setDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean) {
        viewModelScope.launch {
            filterSPInteractor.updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary)
//            getBufferDataFromSpAndCompareFilterSettings()
        }
    }

    fun clearPlaceInDataFilter() {
        viewModelScope.launch {
            filterSPInteractor.clearPlaceInDataFilter()
        }
    }
    fun clearProfessionInDataFilter() {
        viewModelScope.launch {
            filterSPInteractor.clearProfessionInDataFilter()
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
            val filterSettingsBuffer = FilterSettings(
                PlaceSettings(
                    null,
                    null,
                    null,
                    null
                ),
                IndustrySetting(
                    null,
                    null
                ), "", false)
            filterSPInteractor.updateDataFilterBuffer(filterSettingsBuffer)
        }
    }
}
