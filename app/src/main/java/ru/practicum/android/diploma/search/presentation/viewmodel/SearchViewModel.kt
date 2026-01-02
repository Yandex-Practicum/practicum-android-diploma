package ru.practicum.android.diploma.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {
    private val _vacancies = MutableStateFlow<List<VacancyDetail>>(emptyList())
    val vacancies: StateFlow<List<VacancyDetail>> = _vacancies
    var currentPage = 1

    private var latestSearchText: String? = null

    private val searchState = MutableStateFlow<SearchState>(SearchState.Loading)
    fun getSearchState() = searchState.asStateFlow()

    private val _textFieldState = MutableStateFlow(SearchTextFieldState())
    val textFieldState = _textFieldState.asStateFlow()

    fun onQueryChange(query: String) {
        _textFieldState.update {
            it.copy(
                query = query,
                isShowClearIc = query.isNotEmpty()
            )
        }

    }

    fun searchVacancies() {
        val newSearchText = textFieldState.value.query
        if (newSearchText.isNotEmpty()) {
            searchState.update {
                SearchState.Loading
            }
            viewModelScope.launch {
                interactor.getVacancies().collect {

                }
            }
        }

    }

    fun processResult(vacancyResponse: VacancyResponse) {


    }

    private fun renderSearchState(state: SearchState) {
        searchState.update { state }
    }

    companion object {
        private const val MAX_PAGES = 1000
    }

}
