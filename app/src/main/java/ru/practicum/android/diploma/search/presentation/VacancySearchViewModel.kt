package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val interactor: SearchVacancyInteractor,
) : ViewModel() {

    private var latestSearchText: String? = null

    private val vacancyClickEvent = SingleEventLiveData<String>()

    private val vacancyList = MutableLiveData<MutableList<VacancySearch>?>()
    private val pageCount = MutableLiveData<Int>()
    private val stateLiveData = MutableLiveData<VacancySearchScreenState>()

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
                    .collect { foundVacancies ->
                        processingState(foundVacancies)
                    }
            }
        }
    }

    private val vacancySearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadData(changedText)
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

    fun nextPageDebounce(changedText: String) {
        stateLiveData.value = VacancySearchScreenState.PaginationLoading
        pageCount.value = pageCount.value?.plus(1)
        nextSearchDebounce(changedText)
    }

    private val nextSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            loadNextData(changedText)
        }

    private fun loadNextData(changedText: String) {
        query["text"] = changedText
        query["page"] = pageCount.value.toString()
        viewModelScope.launch {
            interactor
                .getVacancyList(query)
                .collect { foundVacancies ->
                    nextPageProcessingState(foundVacancies)
                }
        }
    }

    private fun nextPageProcessingState(foundVacancies: List<VacancySearch>?) {
        when {
            foundVacancies == null -> {
                stateLiveData.value = VacancySearchScreenState.PaginationError("Произошла ошибка")
            }

            foundVacancies.isEmpty() -> {
                stateLiveData.value = VacancySearchScreenState.PaginationError("Вакансий больше нет")
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

    private fun processingState(foundVacancies: List<VacancySearch>?) {
        when {
            foundVacancies == null -> {
                stateLiveData.value = VacancySearchScreenState.ServerError
            }

            foundVacancies.isEmpty() -> {
                stateLiveData.value = VacancySearchScreenState.SearchError
            }

            else -> {
                vacancyList.value = foundVacancies as? MutableList<VacancySearch>?
                stateLiveData.value = VacancySearchScreenState.Content
            }
        }
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
