package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.presentation.ui.model.VacancyListItemUi
import ru.practicum.android.diploma.core.presentation.ui.util.debounce
import ru.practicum.android.diploma.core.presentation.ui.util.formatSalary
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.Result
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyFilter
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val favoritesInteractor: FavoritesVacanciesInteractor
) : ViewModel() {
    private val _vacancies = MutableStateFlow<List<VacancyDetail>>(emptyList())
    val vacancies: StateFlow<List<VacancyDetail>> = _vacancies
    var currentPage = 0
    var maxPages = 0

    private var latestSearchText: String? = null
    val vacanciesList = mutableListOf<VacancyListItemUi>()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Nothing)
    val searchState = _searchState.asStateFlow()

    private val _textFieldState = MutableStateFlow(SearchTextFieldState())
    val textFieldState = _textFieldState.asStateFlow()
    val searchVacanciesDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, false) {
        searchVacancies()
    }

    init {
        observeFavorites()
    }

    fun onQueryChange(query: String) {
        _textFieldState.update {
            it.copy(
                query = query,
                isShowClearIc = query.isNotEmpty()
            )
        }
        searchDebounce(query)

    }

    fun searchVacancies() {
        val newSearchText = textFieldState.value.query
        if (newSearchText.isNotEmpty()) {
            renderSearchState(SearchState.Loading)
            viewModelScope.launch {
                interactor.getVacancies(VacancyFilter(text = newSearchText, page = currentPage)).collect { result ->
                    when (result) {
                        is Result.Error -> processResult(errorMessage = result.message)
                        is Result.Success<VacancyResponse> -> {
                            processResult(result.data)
                            maxPages = result.data.pages
                        }
                    }
                }
            }
        }
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

        searchVacanciesDebounce(changedText)
        removeSearchList()
    }

    fun isFavorite(id: String): Boolean {
        val favorites = favoriteIds.value
        // Источник истины — множество избранных id из БД.
        // Даже если в элементе списка ещё не обновлён флаг isFavorite,
        // доверяем favoritesIds, чтобы избежать мигания иконки на экране деталей.
        if (favorites.contains(id)) return true

        return vacanciesList.firstOrNull { it.id == id }?.isFavorite ?: false
    }

    fun onClearIcClick() {
        onQueryChange("")
        removeSearchList()
    }

    fun onLoadNextPage() {
        renderSearchState(SearchState.Content(vacanciesList, true))
        currentPage++
        viewModelScope.launch {
            interactor.getVacancies(VacancyFilter(text = _textFieldState.value.query, page = currentPage))
                .collect { result ->
                    when (result) {
                        is Result.Error -> processResult(errorMessage = result.message)
                        is Result.Success<VacancyResponse> -> {
                            processResult(result.data)
                            renderSearchState(SearchState.Content(vacanciesList, false))
                        }
                    }
                }
        }
    }

    fun processResult(
        vacancyResponse: VacancyResponse = VacancyResponse(0, 0, 0, emptyList()),
        errorMessage: String = ""
    ) {
        currentPage = vacancyResponse.page
        if (vacancyResponse.vacancies.isNotEmpty()) {
            val currentFavoriteIds = favoriteIds.value
            vacanciesList.addAll(vacancyResponse.vacancies.map {
                VacancyListItemUi(
                    it.id,
                    it.employer.logo,
                    it.name,
                    it.address?.city,
                    it.employer.name,
                    formatSalary(it.salary?.from, it.salary?.to, it.salary?.currency),
                    currentFavoriteIds.contains(it.id)
                )
            })
        }
        when {
            errorMessage != "" -> {
                renderSearchState(
                    SearchState.Error(
                        message = errorMessage
                    )
                )
            }

            else -> {
                renderSearchState(
                    SearchState.Content(
                        vacanciesList
                    )
                )
            }
        }
    }

    private fun removeSearchList() {
        vacanciesList.clear()
        renderSearchState(SearchState.Nothing)
        currentPage = 0
    }

    private fun renderSearchState(state: SearchState) {
        _searchState.update { state }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesInteractor.getFavorites().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _favoriteIds.value = result.data.map { it.id }.toSet()
                    }

                    is Result.Error -> {}
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 3000L
    }

}
