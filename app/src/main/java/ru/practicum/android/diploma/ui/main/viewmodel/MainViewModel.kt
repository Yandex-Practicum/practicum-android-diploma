package ru.practicum.android.diploma.ui.main.viewmodel

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
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.main.VacanciesPagingSource
import ru.practicum.android.diploma.ui.main.MainViewState
import ru.practicum.android.diploma.ui.main.SearchState
import ru.practicum.android.diploma.util.Resource

class MainViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val state = MutableStateFlow(MainViewState())
    fun observeState() = state.asStateFlow()
    private var vacancyJob: Job? = null
    var flow: Flow<PagingData<Vacancy>> = emptyFlow()
        private set

    private fun subscribeVacanciesPagination(query: String) {
        vacancyJob?.cancel()
        flow = Pager(PagingConfig(pageSize = 20)) {
            VacanciesPagingSource(repository, query)
        }.flow.cachedIn(viewModelScope)
        state.update { it.copy(state = SearchState.Content(emptyList())) }
    }

    fun onSearch(text: String) {
        if (text.isEmpty()) {
            state.update { it.copy(state = null) }
            return
        }

        state.update { it.copy(state = SearchState.Loading) }
        viewModelScope.launch {
            searchRequest(text)
        }
    }

    private fun searchRequest(text: String) {
        if (text.isNotEmpty()) {
            viewModelScope.launch {
                state.update { it.copy(state = SearchState.Loading) }
                val result = repository.vacanciesPagination(query = text, nextPage = 1)

                if (result is Resource.Success) {
                    val founded = result.data?.foundedVacancies?.toString()?.let {
                        if (it != "0") {
                            "Найдено $it вакансий"
                        } else {
                            "Таких вакансий нет"
                        }
                    } ?: "Таких вакансий нет"

                    state.update { it.copy(foundVacancies = founded) }
                }

                subscribeVacanciesPagination(text)
            }
        } else {
            state.update { it.copy(state = SearchState.Empty) }
        }
    }
}
