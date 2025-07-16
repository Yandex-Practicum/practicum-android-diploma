package ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.ErrorType
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.paging.VacanciesResult
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.presentation.models.vacancies.VacanciesState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SingleEventLiveData

class VacanciesSearchViewModel(private val interactor: VacanciesInteractor) : ViewModel() {
    private val _state = MutableLiveData<VacanciesState>()
    val state: LiveData<VacanciesState> = _state

    private val _showToast = SingleEventLiveData<Int>()
    val showToast: LiveData<Int> = _showToast

    private var currentQuery = ""
    private var currentPage = 0
    private var totalPages = 0
    private var isLoading = false
    private var hasMore = true
    private val vacancies = mutableListOf<Vacancy>()
    private var lastErrorType: ErrorType? = null
    private var totalFound = 0

    fun searchVacancies(query: String, isNewSearch: Boolean = true) {
        if (query.isEmpty()) {
            resetState()
            return
        }

        if (isNewSearch) {
            handleNewSearch(query)
        } else if (!hasMore || isLoading) {
            return
        }

        isLoading = true
        if (currentPage > 0) {
            _state.value = VacanciesState.LoadingMore
        }

        viewModelScope.launch {
            interactor.search(query, currentPage)
                .flowOn(Dispatchers.IO)
                .collect { resource ->
                    isLoading = false
                    when (resource) {
                        is Resource.Success -> handleSuccess(resource, isNewSearch)
                        is Resource.Error -> handleError(resource)
                    }
                }
        }
    }

    private fun handleNewSearch(query: String) {
        currentQuery = query
        currentPage = 0
        vacancies.clear()
        interactor.clearCache()
        lastErrorType = null
        _state.value = VacanciesState.Loading
    }

    private fun handleSuccess(resource: Resource.Success<VacanciesResult>, isNewSearch: Boolean) {
        resource.data?.let { result ->
            hasMore = result.page < result.pages - 1
            currentPage = result.page + 1
            totalPages = result.pages

            if (isNewSearch) {
                vacancies.clear()
                totalFound = result.totalFound
            }

            val uniqueNewVacancies = result.vacancies.filterNot { newVacancy ->
                vacancies.any { it.id == newVacancy.id }
            }

            vacancies.addAll(uniqueNewVacancies)

            _state.value = if (vacancies.isEmpty()) {
                VacanciesState.Empty
            } else {
                VacanciesState.Success(
                    vacancies = vacancies.toList(),
                    currentPage = result.page,
                    totalPages = result.pages,
                    totalFound = totalFound,
                    hasMore = hasMore
                )
            }
        } ?: run {
            _state.value = VacanciesState.Empty
            if (lastErrorType == null) {
                _showToast.value = R.string.nothing_found
            }
        }
    }

    private fun handleError(resource: Resource.Error<VacanciesResult>) {
        if (lastErrorType != resource.errorType) {
            lastErrorType = resource.errorType
            _showToast.value = when (resource.errorType) {
                ErrorType.NO_INTERNET -> R.string.check_connecton
                ErrorType.SERVER_ERROR -> R.string.server_error
                else -> R.string.unknown_error
            }
        }

        _state.value = if (vacancies.isEmpty()) {
            when (resource.errorType) {
                ErrorType.NO_INTERNET -> VacanciesState.NoInternet
                ErrorType.SERVER_ERROR -> VacanciesState.ServerError
                else -> VacanciesState.Empty
            }
        } else {
            VacanciesState.Success(
                vacancies = vacancies.toList(),
                currentPage = currentPage - 1,
                totalPages = totalPages,
                totalFound = totalFound,
                hasMore = hasMore
            )
        }
    }

    fun loadMore() {
        if (!isLoading && hasMore) {
            searchVacancies(currentQuery, false)
        }
    }

    fun resetState() {
        currentQuery = ""
        currentPage = 0
        totalPages = 0
        hasMore = true
        vacancies.clear()
        _state.value = VacanciesState.Initial
    }

    fun getCurrentQuery(): String = currentQuery
}
