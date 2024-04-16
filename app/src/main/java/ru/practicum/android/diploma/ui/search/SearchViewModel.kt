package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.VacancySearchInteractor
import ru.practicum.android.diploma.domain.filter.FiltersRepository
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class SearchViewModel(
    private val vacancySearchInteractor: VacancySearchInteractor,
    private val filtersRepository: FiltersRepository,
) : ViewModel() {

    var lastQuery = ""
    private var currentPage = 0
    private var maxPagers = 0
    private var vacanciesList = mutableListOf<Vacancy>()
    private var vacanciesFound = 0
    private var filters = Filters()

    private val stateLiveData = MutableLiveData<SearchViewState>()

    fun observeState(): LiveData<SearchViewState> = stateLiveData

    init {
        viewModelScope.launch {
            filtersRepository.getFiltersFlow().collect {
                filters = it
            }
        }
    }

    // Флоу со значинием не пустые ли фильтры, для отображения кнопки фильтров
    val isExistFiltersFlow = flow {
        filtersRepository.getFiltersFlow().map {
            it != Filters()
        }.collect {
            emit(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        filtersRepository.cancelJob()
    }

    fun onLastItemReached() {
        if (currentPage < maxPagers && stateLiveData.value is SearchViewState.Content) {
            search(lastQuery)
        }
    }

    fun setDefaultState() {
        stateLiveData.value = SearchViewState.Default
    }

    fun setContentState() {
        stateLiveData.value = SearchViewState.Content(vacanciesList, vacanciesFound)
    }

    fun clearPagingInfo() {
        lastQuery = ""
        currentPage = 0
        maxPagers = 0
        vacanciesList = mutableListOf()
    }

    fun search(text: String) {
        if (vacanciesList.isEmpty()) {
            stateLiveData.value = SearchViewState.Loading
        } else {
            stateLiveData.value = SearchViewState.RecyclerLoading
        }
        viewModelScope.launch {
            vacancySearchInteractor.getVacancies(text, currentPage, filters).collect {
                if (it.first != null) {
                    handleResponse(it.first as VacancyResponse)
                } else if (it.second != null) {
                    handleError(it.second as String)
                }
            }
        }
        lastQuery = text
    }

    private fun handleResponse(response: VacancyResponse) {
        maxPagers = response.pages
        currentPage = response.page + 1
        if (response.items.isEmpty()) {
            stateLiveData.postValue(SearchViewState.EmptyVacancies)
        } else {
            vacanciesList.addAll(response.items)
            vacanciesFound = response.found
            stateLiveData.postValue(SearchViewState.Content(vacanciesList, vacanciesFound))
        }
    }

    private fun handleError(error: String) {
        if (vacanciesList.isEmpty()) {
            stateLiveData.postValue(SearchViewState.NoInternet)
        } else {
            stateLiveData.postValue(SearchViewState.RecyclerError(error))
        }
    }
}
