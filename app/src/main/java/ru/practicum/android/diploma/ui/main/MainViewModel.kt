package ru.practicum.android.diploma.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.vacancy.api.SearchVacanciesRepository
import ru.practicum.android.diploma.ui.common.SingleLiveEvent
import ru.practicum.android.diploma.ui.main.models.SearchContentStateVO
import ru.practicum.android.diploma.util.debounce

class MainViewModel(
    application: Application,
    private val searchVacanciesRepository: SearchVacanciesRepository,
) : AndroidViewModel(application) {
    private val textLiveData = MutableLiveData("")
    val text: LiveData<String> = textLiveData

    private var pages = 0
    private var page = 0

    private val clearSearchInput = SingleLiveEvent<Unit>()
    fun observeClearSearchInput(): LiveData<Unit> = clearSearchInput

    private val contentStateLiveData = MutableLiveData<SearchContentStateVO>(SearchContentStateVO.Base)
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

    private fun doSearch() {
        val text = text.value ?: ""
        if (text.isEmpty()) {
            return
        }

        contentStateLiveData.postValue(SearchContentStateVO.Loading)
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
        page += 1
        if (page <= pages) {
            doSearch()
        }
    }

    private fun search(options: FilterOptions) {
        viewModelScope.launch {
            val searchResponse = searchVacanciesRepository.search(options)

            contentStateLiveData.postValue(
                when (searchResponse) {
                    is ApiResponse.Success -> {
                        pages = searchResponse.pages
                        page = searchResponse.page
                        searchResponse.data?.let { SearchContentStateVO.Success(it) }
                    }

                    is ApiResponse.Error ->
                        SearchContentStateVO.Error(noInternet = searchResponse.statusCode == -1)
                }
            )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MS = 2000L
    }
}
