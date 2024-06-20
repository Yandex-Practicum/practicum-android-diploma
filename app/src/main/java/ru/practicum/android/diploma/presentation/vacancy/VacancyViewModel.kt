package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.vacancy.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.domain.favorites.VacancyViewState

class VacancyViewModel(
    private val getVacancyDetailsInteractor: GetVacancyDetailsInteractor,
    // private val favoritesVacancyInteractor: FavoritesVacancyInteractor
) : ViewModel() {
    private val _vacancyScreenState = MutableLiveData<VacancyViewState>()
    val vacancyScreenState: LiveData<VacancyViewState> get() = _vacancyScreenState

    var currentDomainVacancy: DomainVacancy? = null
        private set

    fun loadVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            val result = getVacancyDetailsInteractor.execute(vacancyId)
            result.onSuccess {
                currentDomainVacancy = it
                _vacancyScreenState.postValue(VacancyViewState.VacancyDataDetail(it))
                // getFavoriteIds()
            }.onFailure {
                // Ошибка
            }
        }
    }
/*
    fun insertFavoriteVacancy() {
        if (currentVacancy != null) {
            viewModelScope.launch {
                favoritesVacancyInteractor.insertFavoriteVacancy(currentVacancy!!)
            }
            getFavoriteIds()
        }
    }

    fun deleteFavoriteVacancy() {
        if (currentVacancy != null) {
            viewModelScope.launch {
                favoritesVacancyInteractor.deleteFavoriteVacancy(currentVacancy!!)
            }
            getFavoriteIds()
        }
    }

    fun getFavoriteIds() {
        viewModelScope.launch {
            val favoriteIdList = favoritesVacancyInteractor.getFavoriteIds()
            if (favoriteIdList.contains(currentVacancy?.vacancyId)) {
                _vacancyScreenState.postValue(VacancyViewState.VacancyIsFavorite)
            } else {
                _vacancyScreenState.postValue(VacancyViewState.VacancyIsNotFavorite)
            }
        }
    }

 */
}
