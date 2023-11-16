package ru.practicum.android.diploma.presentation.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorite.FavouriteInteractor
import ru.practicum.android.diploma.domain.models.Vacancy


class FavouriteViewModel(val interactor: FavouriteInteractor) : ViewModel() {

    init {
        fill()
    }
    fun fill() {
        viewModelScope.launch {
            interactor.getFavoriteList()
                .collect { vacancies ->
                    renderState(vacancies)
                }
        }
    }

    private val stateFavoriteLiveData = MutableLiveData<List<Vacancy>>()
    fun observeState(): LiveData<List<Vacancy>> = stateFavoriteLiveData

    private fun renderState(vacancies: List<Vacancy>) {
        stateFavoriteLiveData.postValue(vacancies)
    }
}