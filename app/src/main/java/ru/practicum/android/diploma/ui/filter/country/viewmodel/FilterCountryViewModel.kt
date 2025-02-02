package ru.practicum.android.diploma.ui.filter.country.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.areas.api.AreasInteractor
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterCountryViewModel(
    private val interactor: AreasInteractor
) : ViewModel() {

    init {
        searchCountries()
    }

    private val selectCountryTrigger = SingleEventLiveData<Pair<String, String>>()
    fun selectCountryTrigger(): SingleEventLiveData<Pair<String, String>> = selectCountryTrigger

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Loading)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

    fun onItemClicked(countryId: String, countryName: String) {
        selectCountryTrigger.value = Pair(first = countryId, second = countryName)
    }

    private fun searchCountries() {
        viewModelScope.launch {
            interactor
                .getCountries()
                .collect { result ->
                    resultHandle(result)
                }
        }
    }

    private fun resultHandle(result: Resource<List<Area>>) {
        if (result.value != null) {
            searchResultData.postValue(SearchResult.GetAreasContent(result.value))
        } else if (result.errorCode != null) {
            searchResultData.postValue(SearchResult.Error)
        }
    }
}
