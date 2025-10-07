package ru.practicum.android.diploma.presentation.vmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.usecase.SearchVacancyDetailUseCase
import ru.practicum.android.diploma.ui.root.search.VacancyDetailState
import ru.practicum.android.diploma.util.Resource

class VacancyDetailViewModel(
    private val searchVacancyDetailUseCase: SearchVacancyDetailUseCase,
    private val interactorFavorites: FavoritesInteractor
) : ViewModel() {

    private val _vacancyDetailState = MutableLiveData<VacancyDetailState>()
    val vacancyDetailState: LiveData<VacancyDetailState> = _vacancyDetailState

    private var searchJob: Job? = null
    private var addFavoritesJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            performSearch(query)
        }
    }

    fun addFavorites(vacancyId: String){
        addFavoritesJob?.cancel()
        addFavoritesJob = viewModelScope.launch {
            interactorFavorites.setVacancy()
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            when (val result = searchVacancyDetailUseCase.execute(query)) {
                is Resource.Success -> handleSearchSuccess(result)
                is Resource.Error -> null
                is Resource.Loading -> null
            }
        }
    }

    private fun handleSearchSuccess(result: Resource.Success<*>) {
        val searchResult = result.data as ru.practicum.android.diploma.domain.models.SearchResultVacancyDetail
        val vacancyDetail =
            VacancyDetail(
                searchResult.id.toString(),
                searchResult.name.toString(),
                searchResult.description.toString(),
                searchResult.salary,
                searchResult.employer,
                searchResult.industry,
                searchResult.area,
                searchResult.experience,
                searchResult.schedule,
                searchResult.employment
            )
        _vacancyDetailState.value = VacancyDetailState.Success(vacancyDetail)
    }
}
