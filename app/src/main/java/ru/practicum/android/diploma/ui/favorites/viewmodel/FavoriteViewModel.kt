package ru.practicum.android.diploma.ui.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.presentation.favorite.FavoriteVacancyState

class FavoriteViewModel(
    val interactor: FavoriteInteractor
) : ViewModel() {

    private val _vacancyState = MutableLiveData<FavoriteVacancyState>()
    fun vacancyState(): LiveData<FavoriteVacancyState> = _vacancyState

    init {
        fillData()
    }

    fun fillData() {
        renderState(FavoriteVacancyState.Loading)
        viewModelScope.launch {
            interactor.getListVacancy()
                .collect { vacancy ->
                    processResult(vacancy)
                }
        }
    }

    private fun processResult(vacancy: List<VacancyDetailDto>) {
        if (vacancy.isEmpty()) {
            renderState(FavoriteVacancyState.EmptyList)
        } else {
            renderState(FavoriteVacancyState.Content(vacancy))
        }
    }

    private fun renderState(state: FavoriteVacancyState) {
        _vacancyState.postValue(state)
    }
}
