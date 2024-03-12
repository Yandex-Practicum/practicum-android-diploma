package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.VacanciesPagingSource
import ru.practicum.android.diploma.ui.search.SearchState
import ru.practicum.android.diploma.ui.search.SearchViewState
import ru.practicum.android.diploma.util.Resource

class SearchViewModel(
    private val repository: SearchRepository,
    private val filtersRepository: FiltersRepository
) : ViewModel() {

    private val state = MutableStateFlow(SearchViewState())
    fun observeState() = state.asStateFlow()
    var flow: Flow<PagingData<Vacancy>> = emptyFlow()
        private set

    private var query: String = ""

    private fun subscribeVacanciesPagination(params: Map<String, String>) {
        flow = Pager(PagingConfig(pageSize = 20)) {
            VacanciesPagingSource(repository, params, null, null)
        }.flow.cachedIn(viewModelScope)
        state.update { it.copy(state = SearchState.Content(emptyList())) }
    }

    fun onSearch(text: String) {
        query = text
        if (text.isEmpty()) {
            state.update { it.copy(state = null) }
            return
        }

        state.update { it.copy(state = SearchState.Loading) }
        viewModelScope.launch {
            searchRequest()
        }
    }

    private fun searchRequest() {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                state.update { it.copy(state = SearchState.Loading) }
                val filter = filtersRepository.getFilters()

                val params = mutableMapOf("text" to query)
                params["page"] = "1"

                filter.salary?.let {
                    params["salary"] = filter.salary.toString()
                }

                filter.onlyWithSalary?.let {
                    params["only_with_salary"] = filter.onlyWithSalary.toString()
                }

                filter.country?.let {
                    params["area"] = filter.country.toString()
                }

                filter.region?.let {
                    params["area"] = filter.region.toString()
                }

                filter.industry?.let {
                    params["industry"] = filter.industry.toString()
                }

                when (val result = repository.vacanciesPagination(params)) {
                    is Resource.Success -> {
                        val founded = result.data?.foundedVacancies?.toString()?.let {
                            if (it != "0") {
                                "Найдено $it вакансий"
                            } else {
                                "Таких вакансий нет"
                            }
                        } ?: "Таких вакансий нет"

                        state.update { it.copy(foundVacancies = founded) }
                        subscribeVacanciesPagination(params)
                        if (founded == "Таких вакансий нет") {
                            state.update { it.copy(state = SearchState.Empty) }
                        }
                    }

                    is Resource.Error -> {
                        state.update { it.copy(state = SearchState.Error) }
                    }
                    is Resource.ServerError -> {
                        state.update { it.copy(state = SearchState.ServerError) }
                    }
                }
            }
        } else {
            state.update { it.copy(state = SearchState.Empty) }
        }
    }

    fun updatesFilters(gson: String) {
        val filter = Gson().fromJson(gson, Filter::class.java)
        filtersRepository.setFilter(filter)
        searchRequest()
    }
}
