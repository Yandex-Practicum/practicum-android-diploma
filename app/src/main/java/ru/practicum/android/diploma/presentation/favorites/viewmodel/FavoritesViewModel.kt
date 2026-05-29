package ru.practicum.android.diploma.presentation.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.favorites.state.FavoritesUiState

class FavoritesViewModel(
    private val vacancyDbInteractor: VacancyDbInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val state: StateFlow<FavoritesUiState> = _state.asStateFlow()

    private var observeJob: Job? = null

    init {
        observeFavorites()
    }

    fun observeFavorites() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            _state.value = FavoritesUiState.Loading
            vacancyDbInteractor.observeFavoriteVacancies()
                .catch {
                    _state.value = FavoritesUiState.Error
                }
                .collect { favorites ->
                    _state.value = if (favorites.isEmpty()) {
                        FavoritesUiState.Empty
                    } else {
                        FavoritesUiState.Content(favorites.map { it.toVacancy() })
                    }
                }
        }
    }
}

private fun VacancyDetail.toVacancy(): Vacancy = Vacancy(
    id = id,
    name = name,
    company = employer.name,
    city = address?.city,
    salary = salary,
    logo = employer.logo,
)
