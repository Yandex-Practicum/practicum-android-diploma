package ru.practicum.android.diploma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.network.models.HttpErrorType
import ru.practicum.android.diploma.data.network.models.toHttpErrorType
import ru.practicum.android.diploma.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.util.CustomLiveData
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val interactor: VacanciesInteractor,
    private val filterInteractor: FilterSettingsInteractor
) : ViewModel() {

    private var currentSearchPage: Int = 0
    private var maxPages: Int = Int.MAX_VALUE
    private val vacanciesList: MutableList<VacancyCard> = mutableListOf()
    private var isNextPageLoading = false

    private var lastSearchRequest: String = ""
    private var searchJob: Job? = null
    private var lastAppliedFilterSettings: FilterSettings? = null

    private val _searchState: CustomLiveData<SearchState> = CustomLiveData()
    internal val searchState: LiveData<SearchState> get() = _searchState

    private val _isFilterSelected = MutableLiveData<Boolean>()
    val isFilterSelected: LiveData<Boolean> = _isFilterSelected

    private val _searchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changeText ->
            searchVacancies(changeText)
        }

    init {
        _searchState.setValue(SearchState.QueryIsEmpty(isEmpty = true))
        checkFilterState()
    }

    fun checkFilterState() {
        val settings = filterInteractor.getFilterSettings()
        _isFilterSelected.value = settings != FilterSettings()

        if (lastAppliedFilterSettings != null && settings != lastAppliedFilterSettings) {
            if (lastSearchRequest.isNotBlank()) {
                clearPagingHistory()
                searchVacancies(lastSearchRequest)
            }
        }
        lastAppliedFilterSettings = settings
    }

    fun searchDebounce(searchQuery: String) {
        if (searchQuery == lastSearchRequest) {
            return
        }
        if (searchQuery.isBlank()) {
            cancelSearch()
            _searchState.setValue(SearchState.QueryIsEmpty(isEmpty = true))
        } else {
            searchJob?.cancel()
            if (lastSearchRequest.isBlank()) {
                _searchState.setValue(SearchState.QueryIsEmpty(isEmpty = false))
            }
        }
        clearPagingHistory()
        lastSearchRequest = searchQuery
        _searchDebounce(searchQuery)
        _searchState.setStartValue(SearchState.SearchText(searchQuery))
    }

    private fun clearPagingHistory() {
        vacanciesList.clear()
        maxPages = Integer.MAX_VALUE
        currentSearchPage = 0
    }

    private fun searchVacancies(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            renderLoadingState()
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                // Получаем текущие настройки фильтрации перед каждым поиском
                val settings = filterInteractor.getFilterSettings()
                runCatching {
                    interactor.searchVacancies(searchQuery, currentSearchPage, settings)
                        .collect { result ->
                            withContext(Dispatchers.Main) {
                                processSearchResult(result)
                            }
                        }
                }
            }
        }
    }

    private fun processSearchResult(result: ApiResult<ru.practicum.android.diploma.domain.models.VacanciesSearchResult>) {
        val replaceVacancyList = currentSearchPage == 0
        when (result) {
            is ApiResult.Error -> handleError(result.httpCode, replaceVacancyList)
            is ApiResult.Success -> handleSuccess(result.data, replaceVacancyList)
            else -> Unit
        }
    }

    private fun handleSuccess(data: ru.practicum.android.diploma.domain.models.VacanciesSearchResult, replaceVacancyList: Boolean) {
        isNextPageLoading = false
        maxPages = data.pagesCount
        if (data.vacanciesFound > 0) {
            vacanciesList.addAll(data.vacancies)
            renderState(
                SearchState.Content(vacanciesList, currentSearchPage == 0),
                true
            )
            renderState(SearchState.VacanciesCount(data.vacanciesFound))
            ++currentSearchPage
        } else {
            renderState(SearchState.NotFoundError(true), true)
        }
    }

    private fun handleError(httpCode: Int, replaceVacancyList: Boolean) {
        when (httpCode.toHttpErrorType()) {
            HttpErrorType.NETWORK,
            HttpErrorType.UNKNOWN -> {
                if (currentSearchPage == 0) {
                    renderState(SearchState.ConnectionError(true), true)
                } else {
                    _searchState.setSingleEventValue(SearchState.ConnectionError(false))
                }
            }

            HttpErrorType.CLIENT -> {
                renderState(SearchState.NotFoundError(replaceVacancyList), replaceVacancyList)
            }

            HttpErrorType.SERVER -> {
                renderState(SearchState.ServerError500(replaceVacancyList), replaceVacancyList)
            }
        }
    }

    fun forceSearchLastRequest() {
        if (lastSearchRequest.isNotBlank()) {
            _searchState.clear()
            searchVacancies(lastSearchRequest)
        }
    }

    fun setNoScrollOnViewCreated() {
        _searchState.setStartValue(SearchState.Content(vacanciesList, false))
    }

    private fun renderState(newState: SearchState, clearOtherStates: Boolean = false) {
        if (clearOtherStates) {
            _searchState.clear()
            _searchState.setValue(
                SearchState.QueryIsEmpty(
                    isEmpty = lastSearchRequest.isBlank()
                )
            )
            _searchState.setStartValue(SearchState.SearchText(lastSearchRequest))
        }
        _searchState.setValue(newState)
    }

    private fun renderLoadingState() {
        if (currentSearchPage == 0) {
            renderState(SearchState.IsLoading, true)
        } else {
            _searchState.setSingleEventValue(SearchState.IsLoadingNextPage)
        }
    }

    fun onLastItemReached() {
        if (!isNextPageLoading) {
            isNextPageLoading = true
            searchVacancies(lastSearchRequest)
        }
    }

    fun cancelSearch() {
        searchJob?.cancel()
        _searchState.clear()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
