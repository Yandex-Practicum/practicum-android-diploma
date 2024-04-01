package ru.practicum.android.diploma.ui.filter.workplace.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filter.FilterRepositoryFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class CountryViewModel(
    val filterRepositoryFlow: FilterRepositoryFlow
): ViewModel() {

    private val stateLiveData = MutableLiveData<CountryState>()
    fun observeState(): LiveData<CountryState> = stateLiveData

    fun setCountryInfo(country: CountryShared) {
        filterRepositoryFlow.setCountryFlow(country)
    }
}
