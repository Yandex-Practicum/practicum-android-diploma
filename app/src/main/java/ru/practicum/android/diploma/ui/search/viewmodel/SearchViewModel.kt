package ru.practicum.android.diploma.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.filter.api.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.util.SingleEventLiveData

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Empty)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

    private val _filterIconLiveData = MutableLiveData<Boolean>()
    val filterIconLiveData: LiveData<Boolean> = _filterIconLiveData

    private var oldFilterParameters: FilterParameters
    private var newFilterParameters: FilterParameters

    init {
        oldFilterParameters = getFilterSettings()
        newFilterParameters = oldFilterParameters
        isFilterOn()
    }

    private var currentPage: Int = 0
    private var maxPages: Int = 0
    private var isNextPageLoading = false
    private var vacanciesList = arrayListOf<Vacancy>()

    private val openVacancyTrigger = SingleEventLiveData<Long>()

    fun getVacancyTrigger(): SingleEventLiveData<Long> = openVacancyTrigger

    fun clearSearchResults() {
        searchResultData.postValue(SearchResult.Empty)
    }

    fun setNewFilterParameters() {
        newFilterParameters = getFilterSettings()
    }

    fun isFilterOn() {
        _filterIconLiveData.postValue(newFilterParameters != FilterParameters())
    }

    private fun onFilterChanged(): Boolean {
        return oldFilterParameters != newFilterParameters
    }

    fun refreshSearchQuery(text: String?) {
        if (onFilterChanged() && !text.isNullOrEmpty()) {
            searchVacancies(text, true)
            oldFilterParameters = newFilterParameters
        }
    }

    fun searchVacancies(text: String?, isNewRequest: Boolean) {
        val options: HashMap<String, Int> = HashMap()
        searchResultData.postValue(SearchResult.PaginationLoading)
        if (isNewRequest) {
            searchResultData.postValue(SearchResult.Loading)
        }

        isNextPageLoading = true
        options["page"] = currentPage

        if (isNewRequest) {
            isNextPageLoading = false
            options["page"] = 0
            currentPage = 0
            vacanciesList = arrayListOf<Vacancy>()
        }

        viewModelScope.launch {
            vacanciesInteractor
                .searchVacancies(text, options)
                .collect { result ->
                    maxPages = result.value?.pages ?: 0
                    vacanciesList.addAll(result.value?.items ?: emptyList())
                    resultHandle(result)
                    currentPage++
                    isNextPageLoading = false
                }
        }
    }

    private fun resultHandle(result: Resource<VacancyResponse>) {
        if (result.errorCode != null) {
            if (result.errorCode == -1) {
                searchResultData.postValue(SearchResult.NoConnection)
            } else {
                searchResultData.postValue(SearchResult.Error)
            }

        } else if (result.value != null) {
            if (result.value.items.isEmpty()) {
                searchResultData.postValue(SearchResult.NotFound)
            } else {
                searchResultData.postValue(SearchResult.SearchVacanciesContent(result.value.found, vacanciesList))
            }
        }
    }

    fun onVacancyClicked(vacancyId: Long) {
        openVacancyTrigger.value = vacancyId
    }

    fun onLastItemReached(text: String?) {
        if (!isNextPageLoading && currentPage < maxPages) {
            searchVacancies(text, false)
        }

    }

    private fun getFilterSettings(): FilterParameters {
        return filterInteractor.readFromFilterStorage()
    }

}
