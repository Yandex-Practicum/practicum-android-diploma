package ru.practicum.android.diploma.ui.filter.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class WorkplaceViewModel(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
): ViewModel() {

    private var _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    init {
        initSubscribe()
    }

    private fun initSubscribe() {
        viewModelScope.launch {
            filterRepositoryCountryFlow.getCountryFlow()
                .collect { country ->
                    debugLog(TAG) { "filterRepositoryCountryFlow, collect: country = $country" }
                    _countryState.postValue(country)
                }
            // TODO подписка на регионы
        }

//        with(viewModelScope) {
//            launch {
//                // TODO подписка на страны
//            }
//
//            launch {
//                // TODO подписка на регионы
//            }
//        }
    }

    companion object {
        private const val TAG = "WorkplaceViewModel"
    }
}
