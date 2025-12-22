package ru.practicum.android.diploma.favorites.vacancies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity

class FavoritesVacanciesViewModel : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteVacancyEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteVacancyEntity>> = _favorites

}
