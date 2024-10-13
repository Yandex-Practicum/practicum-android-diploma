package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancyListResponseData
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.network.HttpStatusCode

class VacancySearchViewModel(
    private val interactor: SearchVacancyInteractor,
) : ViewModel() {

    private var latestSearchText: String? = null

    private var vacanciesSearchData: VacancyListResponseData? = null
    private val stateLiveData = MutableLiveData<VacancySearchScreenState>()
    private val currentVacancyList = mutableListOf<VacancySearch>()
    fun getStateObserve(): LiveData<VacancySearchScreenState> = stateLiveData
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
                        vacanciesSearchData = pairFoundAndMessage.first
                        processingState(pairFoundAndMessage.first?.items, pairFoundAndMessage.second)
                    }
            }
        }
    }

    private fun loadNextData(changedText: String) {
        query["text"] = changedText
        query["page"] = vacanciesSearchData?.page?.plus(1).toString()
        viewModelScope.launch {
            interactor
                .getVacancyList(query)
                .collect { pairFoundAndMessage ->
                    vacanciesSearchData = pairFoundAndMessage.first
                    nextPageProcessingState(pairFoundAndMessage.first?.items, pairFoundAndMessage.second)
                }
        }
    }

    fun searchDebounce(changedText: String) {
        if (changedText.isEmpty()) {
            stateLiveData.value = VacancySearchScreenState.EmptyScreen
        }
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacancySearchDebounce(changedText)
        }
    }

    private val vacancySearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadData(changedText)
        }

    fun nextPageDebounce(changedText: String) {
        val page = vacanciesSearchData?.page
        val pages = vacanciesSearchData?.pages
        if (page != null &&
            pages != null &&
            page - 1 < pages
        ) {
            stateLiveData.value = VacancySearchScreenState.PaginationLoading
            nextSearchDebounce(changedText)
        }
    }

    private val nextSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadNextData(changedText)
        }

    private fun processingState(foundVacancies: List<VacancySearch>?, errorMessage: HttpStatusCode?) {
        when (errorMessage) {
            HttpStatusCode.OK -> {
                if (foundVacancies != null) {
                    if (foundVacancies.isNotEmpty()) {
                        currentVacancyList.clear()
                        currentVacancyList.addAll(foundVacancies)
                        stateLiveData.value = VacancySearchScreenState.Content(
                            foundVacancies,
                            vacanciesSearchData?.found ?: 0
                        )
                    } else {
                        stateLiveData.value = VacancySearchScreenState.SearchError
                    }
                }
            }

            HttpStatusCode.NOT_CONNECTED -> stateLiveData.value = VacancySearchScreenState.NetworkError
            else -> {
                stateLiveData.value = VacancySearchScreenState.ServerError
            }
        }
    }

    private fun nextPageProcessingState(foundVacancies: List<VacancySearch>?, statusCode: HttpStatusCode?) {
        when (statusCode) {
            HttpStatusCode.OK -> {
                if (foundVacancies != null) {
                    currentVacancyList.addAll(foundVacancies)
                    stateLiveData.value = VacancySearchScreenState.Content(
                        currentVacancyList,
                        vacanciesSearchData?.found ?: 0
                    )
                }
            }

            else -> {
                stateLiveData.value = VacancySearchScreenState.PaginationError
            }
        }
    }

    fun checkVacancyState() {
        if (currentVacancyList.isNotEmpty()) {
            stateLiveData.value =
                VacancySearchScreenState.Content(
                    currentVacancyList,
                    vacanciesSearchData?.found ?: 0
                )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
