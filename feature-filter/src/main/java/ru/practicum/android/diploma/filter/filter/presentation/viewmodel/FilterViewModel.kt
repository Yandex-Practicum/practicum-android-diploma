package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettings
import ru.practicum.android.diploma.filter.filter.domain.usecase.FilterSPInteractor

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {

    private var _filterOptionsListLiveData: MutableLiveData<FilterSettings> = MutableLiveData<FilterSettings>()
    val filterOptionsListLiveData: LiveData<FilterSettings> = _filterOptionsListLiveData

    init {
        getDataFromSp()
    }

    private fun getDataFromSp() {
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
}
