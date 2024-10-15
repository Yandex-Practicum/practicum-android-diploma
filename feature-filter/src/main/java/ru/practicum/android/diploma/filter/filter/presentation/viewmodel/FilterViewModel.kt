package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor
import ru.practicum.android.diploma.filter.place.domain.model.Place

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {
    private var _filterOptionsListLiveData: MutableLiveData<FilterSettingsModel> =
        MutableLiveData<FilterSettingsModel>()
    val filterOptionsListLiveData: LiveData<Map<String, String>> = _filterOptionsListLiveData

    init {
        loadFilterSettings()
    }

    fun loadFilterSettings() {
            viewModelScope.launch(Dispatchers.IO) { filterSPInteractor.getDataFilter().collect{ value ->
                _filterOptionsListLiveData.postValue(value)
            }} }

    fun clearIndustryFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearIndustryFilter()
        }
        }

    fun clearPlaceFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearPlaceFilter()
        }
    }

    fun clearSalaryFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearSalaryFilter()
        }
    }

    fun isDoNotShowWithoutSalaryDataFilter(){
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.isDoNotShowWithoutSalaryDataFilter()
        }
    }

    fun updatePlaceInDataFilter(place: Place) {
        viewModelScope.launch(Dispatchers.IO) { filterSPInteractor.updatePlaceInDataFilter(place).collect{ value ->
            _filterOptionsListLiveData.postValue(value)
        }} }

    fun handleError(){
        // no actions at the moment
    }


}
