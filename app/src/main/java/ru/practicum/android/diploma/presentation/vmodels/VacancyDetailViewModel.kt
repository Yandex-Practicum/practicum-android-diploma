package ru.practicum.android.diploma.presentation.vmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
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
    private val _vacancyFavoriteState = MutableLiveData(false)
    val vacancyFavoriteState: LiveData<Boolean> = _vacancyFavoriteState

    private var searchJob: Job? = null
    private var addFavoritesJob: Job? = null
    private var deleteFavoritesJob: Job? = null
    private var checkFavoritesJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            performSearch(query)
        }
    }

    fun checkFavorites(id: String) {
        checkFavoritesJob?.cancel()
        checkFavoritesJob = viewModelScope.launch {
            interactorFavorites.checkVacancyInFavorite(id).collect { presence ->
                _vacancyFavoriteState.postValue(presence)
            }
        }
    }

    fun addFavorites() {
        if (_vacancyFavoriteState.value == false) {
            addFavoritesJob?.cancel()
            addFavoritesJob = viewModelScope.launch {
                if (_vacancyDetailState.value is VacancyDetailState.Success) {
                    interactorFavorites.setVacancy(
                        with(_vacancyDetailState.value as VacancyDetailState.Success) {
                            Vacancy(
                                id = vacancyDetail.id,
                                title = vacancyDetail.name,
                                description = vacancyDetail.description,
                                salary = vacancyDetail.salary,
                                employer = vacancyDetail.employer,
                                area = vacancyDetail.area
                            )
                        }
                    ).collect { state ->
                        _vacancyFavoriteState.postValue(state)
                    }
                }
            }
        } else {
            deleteFavorites()
        }
    }

    private fun deleteFavorites() {
        deleteFavoritesJob?.cancel()
        deleteFavoritesJob = viewModelScope.launch {
            if (_vacancyDetailState.value is VacancyDetailState.Success) {
                interactorFavorites.deleteVacancyFromFavorite((_vacancyDetailState.value as VacancyDetailState.Success).vacancyDetail.id)
                    .collect { delete ->
                        _vacancyFavoriteState.postValue(!delete)
                    }
            }
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
