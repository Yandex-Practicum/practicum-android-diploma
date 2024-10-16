package ru.practicum.android.diploma.filters.industries.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.industries.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.network.HttpStatusCode

class IndustrySelectViewModel(
    private val interactor: FilterIndustriesInteractor,
    private val requestBuilderInteractor: RequestBuilderInteractor
) : ViewModel() {

    private var stateLiveData = MutableLiveData<IndustrySelectScreenState>()
    fun getStateLiveData(): LiveData<IndustrySelectScreenState> = stateLiveData

    init {
        loadIndustries()
    }

    private var latestSearchText: String? = null
    private var chosenIndustry: Industry? = null

    fun loadIndustries() {
        stateLiveData.value = IndustrySelectScreenState.Loading
        viewModelScope.launch {
            interactor.getIndustries()
                .collect { result ->
                    processingState(result.first, result.second)
                }
        }
    }

    private fun processingState(foundIndustries: List<Industry>?, errorMessage: HttpStatusCode?) {
        when (errorMessage) {
            HttpStatusCode.OK -> {
                if (foundIndustries != null) {
                    if (foundIndustries.isNotEmpty()) {
                        stateLiveData.value = IndustrySelectScreenState.ChooseItem(
                            foundIndustries
                        )
                    } else {
                        stateLiveData.value = IndustrySelectScreenState.Empty
                    }
                }
            }

            HttpStatusCode.NOT_CONNECTED ->
                stateLiveData.value =
                    IndustrySelectScreenState.NetworkError
            else -> {
                stateLiveData.value = IndustrySelectScreenState.ServerError
            }
        }
    }

    private val industrySearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            stateLiveData.postValue(
                IndustrySelectScreenState.FilterRequest(changedText)
            )
        }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            industrySearchDebounce(changedText)
        }
    }

    fun onItemClick(industry: Industry) {
        chosenIndustry = industry
    }

    fun transferIndustryToQuery() {
        if (chosenIndustry != null) {
            requestBuilderInteractor.setIndustry(chosenIndustry!!.id)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
