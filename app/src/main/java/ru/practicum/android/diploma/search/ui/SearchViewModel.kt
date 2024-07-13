package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.utils.VacanciesData
import ru.practicum.android.diploma.search.domain.utils.Options
import ru.practicum.android.diploma.utils.NumericConstants
import ru.practicum.android.diploma.utils.debounce
import java.util.concurrent.atomic.AtomicBoolean

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<SearchState>(SearchState.Empty)
    val screenState: LiveData<SearchState> = _screenState

    private var latestSearchText: String? = null

    private var currentPage = -1
    private var maxPages = 0
    private val vacanciesList = mutableListOf<Vacancy>()

    private val searchDebounce = debounce<String>(
        delayMillis = NumericConstants.TWO_SECONDS,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        currentPage = 0
        maxPages = 0
        vacanciesList.clear()
        searchRequest(changedText)
    }

    private val isNextPageLoading = AtomicBoolean(false)

    fun onLastItemReached() {
        if (isNextPageLoading.get()) {
            return
        }

        if (currentPage < maxPages) {
            currentPage++
            latestSearchText?.let {
                isNextPageLoading.set(true)
                searchRequest(it)
            }
        }
    }

    fun search(searchText: String) {
        if (searchText.isNotEmpty() || latestSearchText != searchText) {
            latestSearchText = searchText
            searchDebounce(searchText)
        } else {
            _screenState.postValue(SearchState.Empty)
        }
    }

    private fun searchRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            _screenState.postValue(SearchState.Loading(currentPage > 0))
            viewModelScope.launch(Dispatchers.IO) {
                val options = Options(
                    searchText = searchText,
                    itemsPerPage = VACANCIES_PER_PAGE,
                    page = currentPage,
                )
                searchInteractor.search(options).collect(::processResponse)
            }
        }
    }

    private fun processResponse(vacanciesData: VacanciesData<VacanciesResponse>) {
        when (vacanciesData) {
            is VacanciesData.Data -> {
                val vacanciesResponse = vacanciesData.value
                with(vacanciesResponse) {
                    currentPage = page
                    vacanciesList += results
                    maxPages = pages
                    _screenState.postValue(SearchState.Content(vacanciesList, foundVacancies))
                }
            }

            else -> {
                _screenState.postValue(SearchState.Error)
            }
        }

        isNextPageLoading.set(false)
    }

    private companion object {
        const val VACANCIES_PER_PAGE = 20
    }
}
