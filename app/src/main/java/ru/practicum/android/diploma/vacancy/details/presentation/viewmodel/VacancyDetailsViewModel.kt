package ru.practicum.android.diploma.vacancy.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.vacancy.details.data.VacancyDetailToFavoriteMapper
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesVacanciesInteractor,
) : ViewModel() {

    private val vacancyDetailToFavoriteMapper = VacancyDetailToFavoriteMapper()
    private val _state = MutableStateFlow(VacancyDetailsState())
    val state: StateFlow<VacancyDetailsState> = _state.asStateFlow()

    private fun checkFavoriteStatus() {
        val vacancyId = _state.value.vacancy?.id ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isFavorite = favoritesInteractor.isFavorite(vacancyId)
            )
        }
    }

    fun toggleFavorite() {
        val currentVacancy = _state.value.vacancy ?: return
        viewModelScope.launch {
            val currentIsFavorite = _state.value.isFavorite

            if (currentIsFavorite) {
                favoritesInteractor.removeFromFavorites(currentVacancy.id)
                _state.value = _state.value.copy(isFavorite = false)
            } else {
                val favoriteEntity = with(vacancyDetailToFavoriteMapper) {
                    currentVacancy.toFavoriteVacancyEntity()
                }
                favoritesInteractor.addToFavorites(favoriteEntity)
                _state.value = _state.value.copy(isFavorite = true)
            }
        }
    }

    fun getShareUrl(): String? {
        return _state.value.vacancy?.url
    }

    fun loadVacancy(
        id: String,
        source: VacancyDetailsSource,
        initialIsFavorite: Boolean = false,
    ) {
        if (initialIsFavorite) {
            _state.value = _state.value.copy(isFavorite = true)
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            interactor.getVacancyById(id, source)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(
                                vacancy = result.data,
                                isLoading = false
                            )
                            if (!initialIsFavorite) {
                                checkFavoriteStatus()
                            }
                        }

                        is Result.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.throwable
                            )
                        }
                    }
                }
        }
    }
}
