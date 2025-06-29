package ru.practicum.android.diploma.ui.main

import android.util.*
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.practicum.android.diploma.data.network.*
import ru.practicum.android.diploma.domain.api.*
import ru.practicum.android.diploma.domain.models.*
import ru.practicum.android.diploma.domain.vacancy.api.*
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.common.*
import ru.practicum.android.diploma.ui.filter.model.*
import ru.practicum.android.diploma.ui.main.models.*
import ru.practicum.android.diploma.util.*

class MainViewModel(
    private val searchVacanciesRepository: SearchVacanciesRepository,
    private val filterPreferences: FilterPreferencesInteractor
) : ViewModel() {
    private var selectedFilters: SelectedFilters? = null

    private val textLiveData = MutableLiveData("")
    val text: LiveData<String> = textLiveData

    init {
        selectedFilters = filterPreferences.loadFilters()
        Log.d("MainViewModel", "selectedFilters: $selectedFilters")
    }

    private val vacanciesList = ArrayList<Vacancy>()
    private var found = 0
    private var pages = 0
    private var page = 0

    private val clearSearchInput = SingleLiveEvent<Unit>()
    fun observeClearSearchInput(): LiveData<Unit> = clearSearchInput

    private val contentStateLiveData =
        MutableLiveData<SearchContentStateVO>(SearchContentStateVO.Base)
    val contentState: LiveData<SearchContentStateVO> = contentStateLiveData

    private val filtersState = MutableLiveData<Boolean>()
    fun observeFiltersState(): LiveData<Boolean> = filtersState

    private val showErrorToast = SingleLiveEvent<Unit>()
    fun observeShowErrorToast(): LiveData<Unit> = showErrorToast

    private val showNoInternetToast = SingleLiveEvent<Unit>()
    fun observeShowNoInternetToast(): LiveData<Unit> = showNoInternetToast

    fun onTextChange(value: String) {
        textLiveData.postValue(value)

        doSearchDebounced(Unit)
    }

    fun onSearchClear() {
        textLiveData.postValue("")
        clearSearchInput.postValue(Unit)
        contentStateLiveData.postValue(SearchContentStateVO.Base)
    }

    private val doSearchDebounced = debounce<Unit>(
        SEARCH_DEBOUNCE_DELAY_MS,
        viewModelScope,
        true,
    ) {
        page = 0
        doSearch()
    }

    private val doSearchNext = debounce<Unit>(
        NEXT_DEBOUNCE_DELAY_MS,
        viewModelScope,
        true,
    ) {
        page += 1
        if (page <= pages) {
            doSearch()
        }
    }

    private fun doSearch() {
        val text = text.value ?: ""
        if (text.isEmpty()) {
            return
        }

        val filters = selectedFilters ?: SelectedFilters(null, null, null, null, null, false)

        contentStateLiveData.postValue(SearchContentStateVO.Loading(page == 0))

        search(
            FilterOptions(
                searchText = text,
                area = filters.region?.id ?: filters.country?.id ?: "",
                industry = filters.industry ?: "",
                salary = filters.salary,
                onlyWithSalary = filters.onlyWithSalary,
                page = page
            )
        )
    }

    fun doNextSearch() {
        doSearchNext(Unit)
    }

    private fun search(options: FilterOptions) {
        viewModelScope.launch {
            handleSearch(options)
        }
    }

    fun onResume() {
        val filters = filterPreferences.loadFilters()
        selectedFilters = filters
        filtersState.postValue(
            filters != null && (
                filters.country != null ||
                    filters.region != null ||
                    filters.industryId != null ||
                    filters.salary != null ||
                    filters.onlyWithSalary
                )
        )

        doSearch()
    }

    private suspend fun handleSearch(options: FilterOptions) {
        val searchResponse = searchVacanciesRepository.search(options)

        if (searchResponse is ApiResponse.Error && options.page != 0) {
            if (searchResponse.statusCode == -1) {
                showNoInternetToast.postValue(Unit)
            } else {
                showErrorToast.postValue(Unit)
            }

            contentStateLiveData.postValue(SearchContentStateVO.Success(vacanciesList, found))

            return
        }

        contentStateLiveData.postValue(
            when (searchResponse) {
                is ApiResponse.Success -> {
                    found = searchResponse.found
                    if (page == 0) {
                        vacanciesList.clear()
                        pages = searchResponse.pages
                        page = searchResponse.page
                    }
                    searchResponse.data?.let {
                        vacanciesList.addAll(it)
                        if (vacanciesList.isEmpty()) {
                            SearchContentStateVO.Error(false)
                        } else {
                            SearchContentStateVO.Success(vacanciesList, searchResponse.found)
                        }
                    }
                }

                is ApiResponse.Error ->
                    SearchContentStateVO.Error(noInternet = searchResponse.statusCode == -1)
            }
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MS = 2000L
        private const val NEXT_DEBOUNCE_DELAY_MS = 300L
    }
}
