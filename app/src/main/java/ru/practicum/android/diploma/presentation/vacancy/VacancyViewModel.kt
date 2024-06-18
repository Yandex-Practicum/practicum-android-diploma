package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyViewState

class VacancyViewModel(private val favoritesVacancyInteractor: FavoritesVacancyInteractor) : ViewModel() {
    private val _vacancyScreenState = MutableLiveData<VacancyViewState>()
    val vacancyScreenState: LiveData<VacancyViewState> get() = _vacancyScreenState

    private var _currentVacancy: Vacancy? = null
    fun setVacancy(vacancy: Vacancy) {
        _currentVacancy = vacancy
        _vacancyScreenState.postValue(VacancyViewState.VacancyDataDetail(vacancy = vacancy))
    }

    fun insertFavoriteVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            favoritesVacancyInteractor.insertFavoriteVacancy(vacancy)
        }
        getFavoriteIds()
    }

    fun deleteFavoriteVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            favoritesVacancyInteractor.deleteFavoriteVacancy(vacancy)
        }
        getFavoriteIds()
    }

    fun getFavoriteIds() {
        viewModelScope.launch {
            val favoriteIdList = favoritesVacancyInteractor.getFavoriteIds()
            if (favoriteIdList.contains(_currentVacancy?.vacancyId)) {
                _vacancyScreenState.postValue(VacancyViewState.VacancyIsFavorite)
            } else {
                _vacancyScreenState.postValue(VacancyViewState.VacancyIsNotFavorite)
            }
        }
    }
}
