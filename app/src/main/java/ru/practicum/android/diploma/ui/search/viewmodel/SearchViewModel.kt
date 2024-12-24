package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.SalaryDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.SearchParams
import ru.practicum.android.diploma.ui.search.state.SingleLiveEvent
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

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

    private val _previousTextInEditText = MutableLiveData<String>()
    val previousTextInEditText: LiveData<String> get() = _previousTextInEditText

    private val _searchJob = MutableLiveData<Job?>()
    val searchJob: LiveData<Job?> get() = _searchJob

    private val _showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = _showToast

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
                handleNetworkError(searchParams.numberOfPage != "0")
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
        response.collect { resp ->
            if (resp.items.isNotEmpty()) {
                updateVacanciesList(resp, searchParams)
                searchScreenStateLiveData.postValue(SearchScreenState.Content(vacanciesList))
                currentPage = resp.page
                maxPages = resp.pages
                if (searchParams.numberOfPage == "0") {
                    _counterVacancy.postValue(resp.found)
                }
            } else {
                searchScreenStateLiveData.postValue(SearchScreenState.NotFound)
                _counterVacancy.postValue(0)
            }
        }
    }

    private fun updateVacanciesList(response: VacancySearchResponse, searchParams: SearchParams) {
        if (searchParams.numberOfPage == "0") {
            vacanciesList.clear()
        }
        val vacancies = response.items.map { vacancyDto ->
            Vacancy(
                vacancyDto.id,
                vacancyDto.name,
                vacancyDto.area.name,
                getCorrectFormOfSalaryText(vacancyDto.salary),
                vacancyDto.employer.name,
                vacancyDto.employer.logoUrls?.original
            )
        }
        vacanciesList.addAll(vacancies)
    }

    private fun handleNetworkError(isPagination: Boolean) {
        if (isPagination) {
            _showToast.postValue(TOAST_ERROR_TEXT)
        } else {
            searchScreenStateLiveData.postValue(SearchScreenState.NetworkError)
            vacanciesList.clear()
        }
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
            val currencySymbol = getCurrencySymbolByCode(salary.currency!!)
            val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            when {
                salary.from != null && salary.to != null && salary.from == salary.to -> {
                    String.format(Locale.getDefault(), "%s %s", numberFormat.format(salary.to), currencySymbol)
                }
                salary.from != null && salary.to == null -> {
                    String.format(Locale.getDefault(), "от %s %s", numberFormat.format(salary.from), currencySymbol)
                }
                salary.from == null && salary.to != null -> {
                    String.format(Locale.getDefault(), "до %s %s", numberFormat.format(salary.to), currencySymbol)
                }
                salary.from != null && salary.to != null -> {
                    String.format(
                        Locale.getDefault(),
                        "от %s до %s %s",
                        numberFormat.format(salary.from),
                        numberFormat.format(salary.to),
                        currencySymbol
                    )
                }
                else -> null
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

    fun getVacanciesText(count: Int = 0): String {
        val lastDigit = count % DIVISOR_FOR_LAST_DIGIT
        val lastTwoDigits = count % DIVISOR_FOR_LAST_TWO_DIGITS

        val isZero = count == 0
        val isOne = lastDigit == ONE && lastTwoDigits != ELEVEN
        val isFew = (lastDigit == TWO || lastDigit == THREE || lastDigit == FOUR) &&
            !(lastTwoDigits == TWELVE || lastTwoDigits == THIRTEEN || lastTwoDigits == FOURTEEN)

        return when {
            isZero -> "Таких вакансий нет"
            isOne -> "Найдена $count вакансия"
            isFew -> "Найдено $count вакансии"
            else -> "Найдено $count вакансий"
        }
    }

    fun updatePreviousTextInEditText(text: String) {
        _previousTextInEditText.postValue(text)
    }

    fun updateSearchJob(job: Job?) {
        _searchJob.postValue(job)
    }

    companion object {
        private const val HTTP_NOT_FOUND = 404
        private const val HTTP_SERVER_ERROR = 500
        private const val TOAST_ERROR_TEXT = "Отсутствует интернет-соединение"
        private const val DIVISOR_FOR_LAST_DIGIT = 10
        private const val DIVISOR_FOR_LAST_TWO_DIGITS = 100
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
        private const val FOUR = 4
        private const val ELEVEN = 11
        private const val TWELVE = 12
        private const val THIRTEEN = 13
        private const val FOURTEEN = 14
    }
}
