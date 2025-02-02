package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.Region

class FilterPlaceOfWorkViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private var country = MutableLiveData<Country?>()
    private var region = MutableLiveData<Region?>()

    init {
        loadData()
    }

    fun loadData() {
        country.postValue(filterInteractor.getCountry())
        region.postValue(filterInteractor.getRegion())
    }

    fun getCountry(): MutableLiveData<Country?> {
        return country
    }

    fun getRegion(): MutableLiveData<Region?> {
        return region
    }
}
