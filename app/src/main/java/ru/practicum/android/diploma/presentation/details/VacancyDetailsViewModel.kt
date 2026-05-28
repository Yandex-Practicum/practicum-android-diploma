package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.DetailsInteractor
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class VacancyDetailsViewModel(
    private val detailsInteractor: DetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val vacancyId: String = savedStateHandle[EXTRA_VACANCY_ID] ?: ""

    private val _uiState = MutableStateFlow<VacancyDetailsUiState>(VacancyDetailsUiState.Loading)
    val uiState: StateFlow<VacancyDetailsUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var currentVacancy: VacancyDetail? = null

    init {
        observeFavoriteState()
        loadVacancyDetails()
    }

    fun loadVacancyDetails() {
        if (vacancyId.isBlank()) {
            _uiState.value = VacancyDetailsUiState.Error(VacancyDetailsUiState.ErrorType.NOT_FOUND)
            return
        }

        _uiState.value = VacancyDetailsUiState.Loading

        viewModelScope.launch {
            val favoriteVacancy = favoritesInteractor.getFavoriteById(vacancyId)

            if (favoriteVacancy != null) {
                currentVacancy = favoriteVacancy
                _uiState.value = VacancyDetailsUiState.Success(favoriteVacancy)
            } else {
                when (val result = detailsInteractor.getVacancyDetails(vacancyId)) {
                    is Resource.Success -> {
                        currentVacancy = result.data
                        _uiState.value = VacancyDetailsUiState.Success(result.data)
                    }

                    is Resource.Error -> {
                        val errorType = when {
                            result.code == CODE_NOT_FOUND -> VacancyDetailsUiState.ErrorType.NOT_FOUND
                            result.message == NO_INTERNET_MESSAGE -> VacancyDetailsUiState.ErrorType.NO_INTERNET
                            else -> VacancyDetailsUiState.ErrorType.SERVER_ERROR
                        }
                        _uiState.value = VacancyDetailsUiState.Error(errorType)
                    }

                    Resource.Loading -> {
                        _uiState.value = VacancyDetailsUiState.Loading
                    }
                }
            }
        }
    }

    fun onFavoriteClicked() {
        val vacancy = currentVacancy ?: return

        viewModelScope.launch {
            if (_isFavorite.value) {
                favoritesInteractor.removeFromFavorites(vacancy.id)
            } else {
                favoritesInteractor.addToFavorites(vacancy)
            }
        }
    }

    private fun observeFavoriteState() {
        if (vacancyId.isBlank()) return

        viewModelScope.launch {
            favoritesInteractor.isFavorite(vacancyId).collect {
                _isFavorite.value = it
            }
        }
    }

    companion object {
        const val EXTRA_VACANCY_ID = "vacancyId"
        private const val CODE_NOT_FOUND = 404
        private const val NO_INTERNET_MESSAGE = "No internet connection"
    }
}
