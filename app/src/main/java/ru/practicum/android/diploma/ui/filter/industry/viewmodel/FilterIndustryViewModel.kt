package ru.practicum.android.diploma.ui.filter.industry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.industries.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry

class FilterIndustryViewModel(
    private val industriesInteractor: IndustriesInteractor
) : ViewModel() {

    private val _industries = MutableLiveData<Resource<List<Industry>>>()
    val industries: LiveData<Resource<List<Industry>>> get() = _industries

    fun loadIndustries() {
        viewModelScope.launch {
            industriesInteractor.getIndustriesList().collect { response ->
                _industries.postValue(response)
            }
        }
    }
}
