package ru.practicum.android.diploma.ui.filter.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industries

class IndustryViewModel(
    private val interactorIndustries: IndustriesInteractor,
) : ViewModel() {
    private val _industryState = MutableLiveData<IndustryState>()
    val industryState: LiveData<IndustryState> = _industryState

    private var fullIndustryList: List<IndustryListItem> = emptyList()

    fun getIndustries() {
        _industryState.postValue(IndustryState.LOADING)
        viewModelScope.launch {
            interactorIndustries.getIndustries().collect { pair ->
                processIndustriesResult(pair.first, pair.second)
            }
        }
    }

    private fun processIndustriesResult(industries: List<Industries>?, error: Int?) {
        if (industries != null) {
            val industryListItems = industries.toIndustryListItems()
            fullIndustryList = industryListItems
            _industryState.postValue(IndustryState.CONTENT(industryListItems))
        } else if (error != null) {
            _industryState.postValue(IndustryState.ERROR(error))
        } else {
            _industryState.postValue(IndustryState.EMPTY)
        }
    }

    fun selectIndustry(industryId: String, currentQuery: String) {
        fullIndustryList = fullIndustryList.map {
            if (it.id == industryId) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }
        filterIndustries(currentQuery)
    }

    fun filterIndustries(query: String) {
        val filteredList = fullIndustryList.filter { item ->
            item.name.contains(query.trim(), ignoreCase = true)
        }
        _industryState.postValue(IndustryState.CONTENT(filteredList))
    }

    fun getSelectedIndustry(): IndustryListItem? {
        return fullIndustryList.find { it.isSelected }
    }
}
