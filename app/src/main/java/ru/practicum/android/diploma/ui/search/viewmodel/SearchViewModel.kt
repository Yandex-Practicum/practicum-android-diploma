package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.SalaryDto
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.SearchParams
import ru.practicum.android.diploma.domain.search.models.SearchScreenState
import java.io.IOException
import java.util.Locale

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    companion object {
        private const val HTTP_NOT_FOUND = 404
        private const val HTTP_SERVER_ERROR = 500
    }

    private val searchScreenStateLiveData = MutableLiveData<SearchScreenState>()
    private var currentPage = 0
    private var maxPages = 0
    private var isNextPageLoading = false
    private val vacanciesList = mutableListOf<Vacancy>()
    private var currentSearchQuery: String = ""

    private val _counterVacancy = MutableLiveData(0)
    val counterVacancy: LiveData<Int> get() = _counterVacancy

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isPaginationLoading = MutableLiveData<Boolean>()
    val isPaginationLoading: LiveData<Boolean> get() = _isPaginationLoading

    fun getSearchScreenStateLiveData(): LiveData<SearchScreenState> = searchScreenStateLiveData

    fun searchVacancies(searchParams: SearchParams) {
        if (isNextPageLoading) return
        isNextPageLoading = true

        updateLoadingState(searchParams)
        currentSearchQuery = searchParams.searchQuery

        viewModelScope.launch {
            try {
                handleSuccessResponse(searchInteractor.getVacancies(searchParams), searchParams)
            } catch (e: IOException) {
                handleNetworkError()
            } catch (e: HttpException) {
                handleHttpError(e)
            } finally {
                resetLoadingState()
            }
        }
    }

    private fun updateLoadingState(searchParams: SearchParams) {
        if (searchParams.numberOfPage == "0") {
            _isLoading.postValue(true)
        } else {
            _isPaginationLoading.postValue(true)
        }
    }

    private suspend fun handleSuccessResponse(
        response: Flow<VacancySearchResponse>,
        searchParams: SearchParams
    ) {
        response.collect { response ->
            if (response.items.isNotEmpty()) {
                updateVacanciesList(response, searchParams)
                searchScreenStateLiveData.postValue(SearchScreenState.Content(vacanciesList))
                currentPage = response.page
                maxPages = response.pages
                if (searchParams.numberOfPage == "0") {
                    _counterVacancy.postValue(response.found)
                }
            } else {
                searchScreenStateLiveData.postValue(SearchScreenState.NotFound)
            }
        }
    }

    private fun updateVacanciesList(response: VacancySearchResponse, searchParams: SearchParams) {
        if (searchParams.numberOfPage == "0") {
            vacanciesList.clear()
        }
        val vacancies = response.items.map {
            Vacancy(
                it.id,
                it.name,
                it.area.name,
                getCorrectFormOfSalaryText(it.salary),
                it.employer.name,
                it.employer.logoUrls?.original
            )
        }
        vacanciesList.addAll(vacancies)
    }

    private fun handleNetworkError() {
        searchScreenStateLiveData.postValue(SearchScreenState.NetworkError)
    }

    private fun handleHttpError(e: HttpException) {
        when (e.code()) {
            HTTP_NOT_FOUND -> searchScreenStateLiveData.postValue(SearchScreenState.NotFound)
            HTTP_SERVER_ERROR -> searchScreenStateLiveData.postValue(SearchScreenState.ServerError)
            else -> searchScreenStateLiveData.postValue(SearchScreenState.Error("HTTP Error: ${e.code()}"))
        }
    }

    private fun resetLoadingState() {
        isNextPageLoading = false
        _isLoading.postValue(false)
        _isPaginationLoading.postValue(false)
    }

    fun onLastItemReached() {
        if (currentPage < maxPages - 1) {
            _isPaginationLoading.postValue(true)
            searchVacancies(
                SearchParams(
                    searchQuery = currentSearchQuery,
                    numberOfPage = (currentPage + 1).toString()
                )
            )
        }
    }

    private fun getCorrectFormOfSalaryText(salary: SalaryDto?): String? {
        return if (salary == null) {
            null
        } else {
            if (salary.from == salary.to) {
                String.format(
                    Locale.getDefault(),
                    "%d %s",
                    salary.to,
                    getCurrencySymbolByCode(salary.currency!!)
                )
            } else if (salary.to == null) {
                String.format(
                    Locale.getDefault(),
                    "от %d %s",
                    salary.from,
                    getCurrencySymbolByCode(salary.currency!!)
                )
            } else {
                String.format(
                    Locale.getDefault(),
                    "от %d до %d %s",
                    salary.from,
                    salary.to,
                    getCurrencySymbolByCode(salary.currency!!)
                )
            }
        }
    }

    private fun getCurrencySymbolByCode(code: String): String {
        return when (code) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "⃀"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "Soʻm"
            else -> ""
        }
    }

    fun resetSearchState() {
        currentSearchQuery = ""
        vacanciesList.clear()
        currentPage = 0
        maxPages = 0
        _counterVacancy.postValue(0)
        searchScreenStateLiveData.postValue(SearchScreenState.Empty)
    }
}
