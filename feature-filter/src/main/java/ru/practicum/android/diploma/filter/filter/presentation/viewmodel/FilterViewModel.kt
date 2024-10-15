package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.place.domain.model.Place

class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {
    private var _filterOptionsListLiveData: MutableLiveData<FilterSettingsModel> =
        MutableLiveData<FilterSettingsModel>()
    val filterOptionsListLiveData: LiveData<FilterSettingsModel> = _filterOptionsListLiveData

    init {
        _filterOptionsListLiveData.value = FilterSettingsModel(null, null, null, null, false)
        loadFilterSettings()
    }

    fun loadFilterSettings() {
        viewModelScope.launch(Dispatchers.IO) { filterSPInteractor.getDataFilter().collect { value ->
            _filterOptionsListLiveData.postValue(value)
        } } }

    fun clearIndustryFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearIndustryFilter()
        }
    }

    fun clearPlaceFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearPlaceFilter()
        }
    }

    fun clearSalaryFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.clearSalaryFilter()
        }
    }

    fun isDoNotShowWithoutSalaryDataFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            filterSPInteractor.isDoNotShowWithoutSalaryDataFilter()
        }
    }

    fun updatePlaceInDataFilter(place: Place) {
        viewModelScope.launch(Dispatchers.IO) { filterSPInteractor.updatePlaceInDataFilter(place).collect { value ->
            if (value == -1) handleError()
        } } }

    fun updateProfessionInDataFilter(industryModel: IndustryModel) {
        viewModelScope.launch(
            Dispatchers.IO
        ) { filterSPInteractor.updateProfessionInDataFilter(industryModel).collect { value ->
            if (value == -1) handleError()
        } } }

    fun updateDoNotShowWithoutSalaryInDataFilter(switch: Boolean) {
        viewModelScope.launch(
            Dispatchers.IO
        ) { filterSPInteractor.updateDoNotShowWithoutSalaryInDataFilter(switch).collect { value ->
            if (value == -1) handleError()
        } } }

    fun updateSalaryInDataFilter(salaryExpectation: Int) {
        viewModelScope.launch(
            Dispatchers.IO
        ) { filterSPInteractor.updateSalaryInDataFilter(salaryExpectation).collect { value ->
            if (value == -1) handleError()
        } } }

    private fun handleError() {
        // no actions at the moment
    }

}
