package ru.practicum.android.diploma.ui.filter.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class WorkplaceViewModel(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
): ViewModel() {

    private var _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    init {
        getCountryInfo()
    }

    fun getCountryInfo() {
        viewModelScope.launch {
            filterRepositoryCountryFlow.getCountryFlow()
                .collect { country ->
                    _countryState.postValue(country)
                }
        }
    }
}
