package ru.practicum.android.diploma.presentation.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Page
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce


class SearchViewModel(
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 5000L
    }

    private var lastSearch: String = ""
    private var page = 0
    private var pages = 0
    var isNextPageLoading = false
    private val vacancies = ArrayList<Vacancy>()
    private var isPadding = false
    private val screenState = MutableLiveData<SearchScreenState>(SearchScreenState.StartScreen)
    fun getScreenState(): LiveData<SearchScreenState> = screenState

    val searchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { request ->

        if (request != lastSearch) {
            lastSearch = request
            screenState.value = SearchScreenState.Loading
            startSearch(request, 0)

        }

    }

    private fun startSearch(request: String, page: Int) {
        val options: HashMap<String, String> = HashMap()
        options["text"] = request
        if (page != 0) {
            options["page"] = page.toString()
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vacancyInteractor
                    .getVacancies(options)
                    .collect { result -> resultHandler(result) }
            }
        }
    }

    private fun resultHandler(result: Resource<Page>) {
        val state = when (result) {
            is Resource.Error -> {
                if (!isPadding) {
                    SearchScreenState.ServerError
                } else {
                    SearchScreenState.ErrInPagging
                }
            }

            is Resource.Success -> {
                if (!isPadding) {
                    vacancies.clear()
                }
                page = result.data.page
                pages = result.data.pages
                vacancies.addAll(result.data.vacancies)
                if (vacancies.isEmpty()) {
                    SearchScreenState.EmptyList
                }
                SearchScreenState.ShowVacancies(vacancies)
            }

        }
        screenState.postValue(state)
        isPadding=false

    }

    fun onLastItemReached() {
        if((page<(pages-1)) && !isPadding){
            isPadding=true
            screenState.value=SearchScreenState.LoadNextPage
            startSearch(lastSearch,++page)
        }
        Log.d("mytag", "---onLastItemReached:--- ")
    }

}
