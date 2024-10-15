package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.filter.domain.model.FilterSettingsModel
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor

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
        _filterOptionsListLiveData.postValue(filterSPInteractor.getDataFilter())
    }

    fun putValue(key: String, value: String) {
        filterSPInteractor.putValue(key, value)
    }

    fun removeValue(key : String){

    }
}
