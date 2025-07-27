package ru.practicum.android.diploma.search.presenter.search

import android.util.Log
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

    private val _currentPageState = MutableStateFlow<Int>(0)

    private var currentText = ""
    private var currentFilters: Map<String, String?> = emptyMap()
    private var vacanciesList = mutableListOf<VacancyPreviewUi>()
    private var maxPages = Int.MAX_VALUE
    private var isLoading = false


    init {
        getFiltersState()
    }

    fun searchVacancies(text: String, filters: Map<String, String?> = emptyMap()) {
        if (isLoading) return
        currentText = text
        currentFilters = filters
        Log.d("Filters", filters.toString())
        _currentPageState.value = 0
        vacanciesList.clear()
        maxPages = Int.MAX_VALUE
        loadPage()
    }

    fun updatePage() {
        if (isLoading || _currentPageState.value >= maxPages - 1) return
        _currentPageState.value += 1
        loadPage()
    }

    fun loadFiltersFromStorage() = filterInteractor.getSavedFilters()

    private fun loadPage() {
        isLoading = true
        viewModelScope.launch {
            searchInteractor.getVacancies(currentText, _currentPageState.value, currentFilters)
                .onStart {
                    _state.value = if (_currentPageState.value == 0) SearchState.Loading else SearchState.LoadingMore
                }.collect { pair ->
                    val newData = pair.first
                    val message = pair.second
                    when {
                        !newData.isNullOrEmpty() -> {
                            maxPages = newData.first().pages
                            val uiData = newData.map { it.toUiModel() }
                            vacanciesList.addAll(uiData) // Добавляем новые данные в конец
                            _state.value = SearchState.Content(vacanciesList.toList())
                            isLoading = false
                        }

                        message == FailureType.NoInternet -> {
                            isLoading = false
                            _state.value = SearchState.NoInternet
                        }

                        message == FailureType.NotFound || newData.isNullOrEmpty() -> {
                            maxPages = _currentPageState.value
                            isLoading = false
                            if (vacanciesList.isEmpty()) {
                                _state.value = SearchState.NotFound
                            } else {
                                _state.value = SearchState.Content(vacanciesList.toList())
                            }
                        }

                        message == FailureType.ApiError -> {
                            isLoading = false
                            _state.value = SearchState.Error
                        }
                    }
                }
        }
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
        var empty = false
        when {
            currentFilters.first == null && currentFilters.second == null && !currentFilters.third -> {
                empty = true
            }

            else -> {
                empty = false
            }
        }
        Log.d("currentFilters", empty.toString())
        if (empty) {
            _filterState.value = FilterState.Empty
        } else {
            _filterState.value = FilterState.Saved
        }
    }
}
