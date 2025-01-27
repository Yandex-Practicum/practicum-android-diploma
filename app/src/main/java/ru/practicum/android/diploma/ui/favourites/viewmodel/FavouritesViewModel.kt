package ru.practicum.android.diploma.ui.favourites.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouritesViewModel(
    private val interactor: FavoritesInteractor
) : ViewModel() {

    init {
        loadFavorites()
    }

    private val _favoriteVacancies = MutableLiveData<Resource<List<Vacancy>>>()
    val favoriteVacancies: LiveData<Resource<List<Vacancy>>> = _favoriteVacancies

    fun onVacancyClicked(vacancy: Vacancy) {
        Log.i("isClicked", "Пользователь нажал на вакансию")
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            interactor.getFavorites()
                .collect { resource ->
                    _favoriteVacancies.value = resource
                }
        }
    }
}
