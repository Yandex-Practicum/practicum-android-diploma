package ru.practicum.android.diploma.ui.similarvacancies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.SimilarRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.VacanciesPagingSource
import ru.practicum.android.diploma.ui.search.SearchState
import ru.practicum.android.diploma.ui.search.SearchViewState
import ru.practicum.android.diploma.util.Resource

class SimilarViewModel(
    private val repository: SimilarRepository,
) : ViewModel() {

    private val state = MutableStateFlow(SearchViewState())
    fun observeState() = state.asStateFlow()
    private var vacancyJob: Job? = null
    var flow: Flow<PagingData<Vacancy>> = emptyFlow()

    private var currentVacancyId: String = ""

    private fun subscribeVacanciesPagination(vacancyId: String) {
        vacancyJob?.cancel()
        flow = Pager(PagingConfig(pageSize = 20)) {
            VacanciesPagingSource(null, null, repository, vacancyId)
        }.flow.cachedIn(viewModelScope)
        state.update { it.copy(state = SearchState.Content(emptyList())) }
    }

    fun onSearch(vacancyId: String) {
        currentVacancyId = vacancyId
        state.update { it.copy(state = SearchState.Loading) }
        viewModelScope.launch {
            searchRequest()
        }
    }

    private fun searchRequest() {
        viewModelScope.launch {
            state.update { it.copy(state = SearchState.Loading) }

            val result = repository.similarVacanciesPagination(currentVacancyId, 1)

            when (result) {
                is Resource.Success -> {
                    val founded = result.data?.foundedVacancies ?: state.update { it.copy(state = SearchState.Empty) }
                    if (founded == 0) {
                        state.update { it.copy(state = SearchState.Empty) }
                    }
                    subscribeVacanciesPagination(currentVacancyId)
                }
                is Resource.Error -> {
                    state.update { it.copy(state = SearchState.Error) }
                }
                is Resource.ServerError -> {
                    state.update { it.copy(state = SearchState.ServerError) }
                }
            }
        }
    }

}
