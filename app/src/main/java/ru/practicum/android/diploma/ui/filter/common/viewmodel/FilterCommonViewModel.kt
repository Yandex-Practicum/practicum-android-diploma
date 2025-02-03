package ru.practicum.android.diploma.ui.filter.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.data.filter.FilterParameters
import ru.practicum.android.diploma.domain.filter.api.FilterInteractor

class FilterCommonViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private var oldFilterParameters: FilterParameters
    private var newFilterParameters: FilterParameters

    private val _filterParamLiveData = MutableLiveData<FilterParameters>()
    val filterParamLiveData: LiveData<FilterParameters> = _filterParamLiveData

    private val _applyButtonLiveData = MutableLiveData<Boolean>()
    val acceptButtonLiveData: LiveData<Boolean> = _applyButtonLiveData

    private val _resetButtonLiveData = MutableLiveData<Boolean>()
    val resetButtonLiveData: LiveData<Boolean> = _resetButtonLiveData

    init {
        oldFilterParameters = getFilterSettings()
        newFilterParameters = oldFilterParameters
        _filterParamLiveData.postValue(newFilterParameters)
        isModelEmpty()
    }

    fun setCountryAndRegionParams(
        countryId: String?,
        countryName: String?,
        regionId: String?,
        regionName: String?
    ) {
        newFilterParameters = newFilterParameters.copy(
            countryId = countryId,
            countryName = countryName,
            regionId = regionId,
            regionName = regionName
        )
        _filterParamLiveData.postValue(newFilterParameters)
        isModelsEqual()
        isModelEmpty()
    }

    fun setIndustryParams(industryId: String?, industryName: String?) {
        newFilterParameters = newFilterParameters.copy(industryId = industryId, industryName = industryName)
        _filterParamLiveData.postValue(newFilterParameters)
        isModelsEqual()
        isModelEmpty()
    }

    fun setExpectedSalaryParam(expectedSalary: Int?) {
        if (newFilterParameters.expectedSalary != expectedSalary) {
            newFilterParameters = newFilterParameters.copy(expectedSalary = expectedSalary)
            _filterParamLiveData.postValue(newFilterParameters)
            isModelsEqual()
            isModelEmpty()
        }
    }

    fun setIsWithoutSalaryShowed(isWithoutSalaryShowed: Boolean) {
        newFilterParameters = newFilterParameters.copy(isWithoutSalaryShowed = isWithoutSalaryShowed)
        _filterParamLiveData.postValue(newFilterParameters)
        isModelsEqual()
        isModelEmpty()
    }

    fun saveFilterSettings() {
        filterInteractor.saveToFilterStorage(
            newFilterParameters
        )
    }

    private fun getFilterSettings(): FilterParameters {
        return filterInteractor.readFromFilterStorage()
    }

    fun resetFilter() {
        newFilterParameters = FilterParameters(isWithoutSalaryShowed = false)
        _filterParamLiveData.postValue(newFilterParameters)
        isModelsEqual()
        isModelEmpty()
    }

    private fun isModelsEqual() {
        _applyButtonLiveData.postValue(oldFilterParameters == newFilterParameters)
    }

    private fun isModelEmpty() {
        _resetButtonLiveData.postValue(newFilterParameters == FilterParameters())
    }

}
