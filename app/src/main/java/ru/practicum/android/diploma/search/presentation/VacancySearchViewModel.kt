package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancyListResponseData
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.network.HttpStatusCode

class VacancySearchViewModel(
    private val interactor: SearchVacancyInteractor,
) : ViewModel() {

    private var latestSearchText: String? = null

    private val vacancyClickEvent = SingleEventLiveData<String>()

    private val vacanciesSearchData = MutableLiveData<VacancyListResponseData>()
    private val vacancyList = MutableLiveData<MutableList<VacancySearch>?>()
    private val pageCount = MutableLiveData<Int>()
    private val stateLiveData = MutableLiveData<VacancySearchScreenState>()

    fun getVacanciesSearchDataObserve(): LiveData<VacancyListResponseData> = vacanciesSearchData
    fun getVacancyClickEvent(): LiveData<String> = vacancyClickEvent
    fun getStateObserve(): LiveData<VacancySearchScreenState> = stateLiveData
    fun getVacancyListObserve(): LiveData<MutableList<VacancySearch>?> = vacancyList
    private val query = HashMap<String, String>()

    fun loadData(text: String) {
        if (text.isNotEmpty()) {
            stateLiveData.value = VacancySearchScreenState.Loading
            query["text"] = text
            query["page"] = "0"
            viewModelScope.launch {
                interactor
                    .getVacancyList(query)
                    .collect { pairFoundAndMessage ->
                        vacanciesSearchData.value = pairFoundAndMessage.first
                        processingState(pairFoundAndMessage.first?.items, pairFoundAndMessage.second)
                    }
            }
        }
    }

    private fun loadNextData(changedText: String) {
        query["text"] = changedText
        query["page"] = vacanciesSearchData.value?.page?.plus(1).toString()
        viewModelScope.launch {
            interactor
                .getVacancyList(query)
                .collect { pairFoundAndMessage ->
                    vacanciesSearchData.value = pairFoundAndMessage.first
                    nextPageProcessingState(pairFoundAndMessage.first?.items)
                }
        }
    }

    fun searchDebounce(changedText: String) {
        if (changedText.isEmpty()) {
            stateLiveData.value = VacancySearchScreenState.EmptyScreen
        }
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            pageCount.value = 0
            vacancySearchDebounce(changedText)
        }
    }

    private val vacancySearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadData(changedText)
        }

    fun nextPageDebounce(changedText: String) {
        if (vacanciesSearchData.value!!.pages - 1 > vacanciesSearchData.value!!.page) {
            stateLiveData.value = VacancySearchScreenState.PaginationLoading
            nextSearchDebounce(changedText)
        }
    }

    private val nextSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadNextData(changedText)
        }

    private fun processingState(foundVacancies: List<VacancySearch>?, errorMessage: HttpStatusCode?) {
        when {
            foundVacancies == null -> {
                when (errorMessage) {
                    HttpStatusCode.NOT_CONNECTED -> stateLiveData.value = VacancySearchScreenState.NetworkError
                    HttpStatusCode.INTERNAL_SERVER_ERROR -> stateLiveData.value = VacancySearchScreenState.ServerError
                    else -> {
                        stateLiveData.value = VacancySearchScreenState.ServerError
                    }
                }
            }

            foundVacancies.isEmpty() -> {
                stateLiveData.value = VacancySearchScreenState.SearchError
            }

            else -> {
                if (vacancyList.value.isNullOrEmpty()) {
                    vacancyList.value = mutableListOf()
                }
                vacancyList.value?.clear()
                vacancyList.value?.addAll(foundVacancies)
                vacancyList.value = vacancyList.value
                stateLiveData.value = VacancySearchScreenState.Content
            }
        }
    }

    private fun nextPageProcessingState(foundVacancies: List<VacancySearch>?) {
        when {
            foundVacancies == null -> {
                stateLiveData.value = VacancySearchScreenState.PaginationError
            }

            else -> {
                vacancyList.value?.addAll(foundVacancies)
                vacancyList.value = vacancyList.value
                stateLiveData.value = VacancySearchScreenState.Content
            }
        }
    }

    fun onVacancyClick(vacancySearch: VacancySearch) {
        vacancyClickEvent.value = vacancySearch.id
    }

    fun checkVacancyState() {
        if (!vacancyList.value.isNullOrEmpty()) {
            vacancyList.value = vacancyList.value
            stateLiveData.value = VacancySearchScreenState.Content
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
