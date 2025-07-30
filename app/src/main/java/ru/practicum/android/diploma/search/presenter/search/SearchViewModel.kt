package ru.practicum.android.diploma.search.presenter.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.FiltersInteractor
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.VacancyPreview
import ru.practicum.android.diploma.search.presenter.model.SearchState
import ru.practicum.android.diploma.search.presenter.model.VacancyPreviewUi
import ru.practicum.android.diploma.util.VacancyFormatter

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val filterInteractor: FiltersInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Empty)
    val state: StateFlow<SearchState> = _state

    private val _filterState = MutableStateFlow<FilterState>(FilterState.Empty)
    val filterState: StateFlow<FilterState> = _filterState

    private val _currentPageState = MutableStateFlow(0)

    private var currentText = ""
    private var currentFilters: Map<String, String?> = emptyMap()
    private var vacanciesList = mutableListOf<VacancyPreviewUi>()
    private var maxPages = Int.MAX_VALUE
    private var isLoading = false
    private var paginationErrorOccurred = false

    private var currentRequestId = 0

    init {
        getFiltersState()
    }

    fun searchVacancies(text: String, filters: Map<String, String?> = emptyMap()) {
        if (isLoading) return

        currentText = text
        currentFilters = filters
        _currentPageState.value = 0
        vacanciesList.clear()
        maxPages = Int.MAX_VALUE
        paginationErrorOccurred = false

        currentRequestId++
        val requestId = currentRequestId

        loadPage(requestId)
    }

    fun updatePage() {
        if (isLoading || _currentPageState.value >= maxPages - 1 || paginationErrorOccurred) return
        _currentPageState.value += 1

        val requestId = currentRequestId
        loadPage(requestId)
    }

    fun loadFiltersFromStorage() = filterInteractor.getSavedFilters()

    fun onInternetAppeared() {
        if (paginationErrorOccurred) {
            paginationErrorOccurred = false
            updatePage()
        }
    }

    private fun loadPage(requestId: Int) {
        isLoading = true
        viewModelScope.launch {
            searchInteractor.getVacancies(currentText, _currentPageState.value, currentFilters)
                .onStart { showLoadingState() }
                .collect { pair ->
                    if (requestId == currentRequestId) {
                        processSearchResult(pair)
                    }
                    isLoading = false
                }
        }
    }

    private fun showLoadingState() {
        val newState = if (_currentPageState.value == 0) {
            SearchState.Loading
        } else {
            SearchState.LoadingMore
        }
        _state.value = newState
    }

    private fun processSearchResult(pair: Pair<List<VacancyPreview>?, FailureType?>) {
        val newData = pair.first
        val message = pair.second

        when {
            !newData.isNullOrEmpty() -> handleSuccess(newData)
            message == FailureType.NoInternet -> handleNoInternet()
            message == FailureType.ApiError -> handleApiError()
            message == FailureType.NotFound || newData.isNullOrEmpty() -> handleNotFound()
        }
    }

    private fun handleSuccess(newData: List<VacancyPreview>) {
        maxPages = newData.first().pages
        val uiData = newData.map { it.toUiModel() }
        vacanciesList.addAll(uiData)
        _state.value = SearchState.Content(vacanciesList.toList())
        isLoading = false
    }

    private fun handleNoInternet() {
        isLoading = false
        val isPaginationError = _currentPageState.value > 0
        if (isPaginationError) {
            paginationErrorOccurred = true
        }
        _state.value = SearchState.NoInternet(isPaginationError = isPaginationError)
    }

    private fun handleApiError() {
        isLoading = false
        _state.value = SearchState.Error
    }

    private fun handleNotFound() {
        maxPages = _currentPageState.value
        isLoading = false
        if (vacanciesList.isEmpty()) {
            _state.value = SearchState.NotFound
        } else {
            _state.value = SearchState.Content(vacanciesList.toList())
        }
    }

    fun resetStateIfQueryIsEmpty() {
        currentText = ""
        _state.value = SearchState.Empty
        currentRequestId++
    }

    private fun VacancyPreview.toUiModel(): VacancyPreviewUi {
        return VacancyPreviewUi(
            id = id,
            found = found,
            name = name,
            employerName = employerName,
            salary = VacancyFormatter.formatSalary(from, to, currency),
            logoUrl = url
        )
    }

    fun getFiltersState() {
        val currentFilters = filterInteractor.getSavedFilters()
        val isEmpty = currentFilters.first == null &&
            currentFilters.second == null &&
            !currentFilters.third

        if (isEmpty) {
            _filterState.value = FilterState.Empty
        } else {
            _filterState.value = FilterState.Saved
        }
    }
}
