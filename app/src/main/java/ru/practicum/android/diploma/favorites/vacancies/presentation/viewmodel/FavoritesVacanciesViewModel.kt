package ru.practicum.android.diploma.favorites.vacancies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor

class FavoritesVacanciesViewModel(
    private val interactor: FavoritesVacanciesInteractor
) : ViewModel() {

    private val _favorites =
        MutableStateFlow<List<FavoriteVacancyEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteVacancyEntity>> = _favorites

    init {
        viewModelScope.launch {
            interactor.getFavorites().collect {
                _favorites.value = it
            }
        }
    }
}
