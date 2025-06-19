package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.vacancy.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.common.SingleLiveEvent
import ru.practicum.android.diploma.ui.main.models.SearchContentStateVO
import ru.practicum.android.diploma.util.debounce

class MainViewModel(
    private val searchVacanciesRepository: SearchVacanciesRepository,
) : ViewModel() {
    private val textLiveData = MutableLiveData("")
    val text: LiveData<String> = textLiveData

    private val vacanciesList = ArrayList<Vacancy>()
    private var pages = 0
    private var page = 0

    private val clearSearchInput = SingleLiveEvent<Unit>()
    fun observeClearSearchInput(): LiveData<Unit> = clearSearchInput

    private val contentStateLiveData =
        MutableLiveData<SearchContentStateVO>(SearchContentStateVO.Base)
    val contentState: LiveData<SearchContentStateVO> = contentStateLiveData

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

        contentStateLiveData.postValue(SearchContentStateVO.Loading(page == 0))
        search(
            FilterOptions(
                searchText = text,
                area = "",
                industry = "",
                currency = "",
                salary = null,
                onlyWithSalary = true,
                page = page
            )
        )
    }

    fun doNextSearch() {
        doSearchNext(Unit)
    }

    private fun search(options: FilterOptions) {
        viewModelScope.launch {
            val searchResponse = searchVacanciesRepository.search(options)

            contentStateLiveData.postValue(
                when (searchResponse) {
                    is ApiResponse.Success -> {
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
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MS = 2000L
        private const val NEXT_DEBOUNCE_DELAY_MS = 300L
    }
}
