package ru.practicum.android.diploma.favorites.vacancies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.search.domain.model.Result as FavoritesResult

class FavoritesVacanciesViewModel(
    private val interactor: FavoritesVacanciesInteractor
) : ViewModel() {

    private val _favorites =
        MutableStateFlow<List<FavoriteVacancyEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteVacancyEntity>> = _favorites

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    init {
        viewModelScope.launch {
            interactor.getFavorites().collect { result ->
                when (result) {
                    is FavoritesResult.Success -> {
                        _favorites.value = result.data
                        _isError.value = false
                    }

                    is FavoritesResult.Error -> {
                        _favorites.value = emptyList()
                        _isError.value = true
                    }
                }
            }
        }
    }
}
