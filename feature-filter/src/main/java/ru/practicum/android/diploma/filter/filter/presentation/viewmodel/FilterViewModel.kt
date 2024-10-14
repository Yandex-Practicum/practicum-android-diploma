package ru.practicum.android.diploma.filter.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor

const val ARGS_INDUSTRY_ID = "industry_id"
const val ARGS_INDUSTRY_NAME = "industry_name"
class FilterViewModel(
    private val filterSPInteractor: FilterSPInteractor
) : ViewModel() {
    private var _filterOptionsListLiveData: MutableLiveData<Map<String, String>> =
        MutableLiveData<Map<String, String>>()
    val filterOptionsListLiveData: LiveData<Map<String, String>> = _filterOptionsListLiveData

    init {
        loadFilterSettings()
    }

    fun loadFilterSettings() {
        _filterOptionsListLiveData.postValue(filterSPInteractor.getAll())
    }

    fun putValue(key: String, value: String) {
        filterSPInteractor.putValue(key, value)
    }
}
