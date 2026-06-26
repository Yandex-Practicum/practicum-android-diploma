package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.ApiResult

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val vacanciesInteractor: VacanciesInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyDetailsState>(VacancyDetailsState.Loading)
    val state: StateFlow<VacancyDetailsState> = _state.asStateFlow()

    private val _isFavoriteState = MutableStateFlow(false)
    val isFavoriteState: StateFlow<Boolean> = _isFavoriteState.asStateFlow()

    private val _events = MutableSharedFlow<VacancyDetailsEvent>()
    val events = _events.asSharedFlow()

    init {
        loadVacancyDetails()
    }

    private fun loadVacancyDetails() {
        viewModelScope.launch {
            _isFavoriteState.value = favoritesInteractor.isInFavorites(vacancyId)

            vacanciesInteractor.getVacancyDetails(vacancyId).collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _state.value = VacancyDetailsState.Loading
                    }

                    is ApiResult.Success -> {
                        _state.value = VacancyDetailsState.Content(result.data)
                    }

                    is ApiResult.Error -> {
                        val vacancy = favoritesInteractor.getVacancy(vacancyId)
                        if (vacancy != null) {
                            _state.value = VacancyDetailsState.Content(vacancy)
                        } else {
                            _state.value = VacancyDetailsState.Error
                        }
                    }
                }
            }
        }
    }

    // обработка одноразовых событий
    fun onPhoneClick() {
        val currentState = _state.value

        if (currentState is VacancyDetailsState.Content) {
            viewModelScope.launch {
                val phone = currentState.vacancy.phoneFormatted

                if (phone != null) {
                    _events.emit(VacancyDetailsEvent.OpenPhone(phone))
                }
            }
        }
    }

    fun onEmailClick() {
        val currentState = _state.value

        if (currentState is VacancyDetailsState.Content) {
            viewModelScope.launch {
                val email = currentState.vacancy.contactEmail

                if (email != null) {
                    _events.emit(VacancyDetailsEvent.OpenEmail(email))
                }
            }
        }
    }

    fun onFavoritesClick() {
        viewModelScope.launch {
            val isFavorite = favoritesInteractor.isInFavorites(vacancyId)

            if (isFavorite) {
                favoritesInteractor.removeVacancy(vacancyId)
            } else {
                val state = _state.value
                if (state is VacancyDetailsState.Content) {
                    favoritesInteractor.addVacancy(state.vacancy)
                }
            }

            _isFavoriteState.value =
                favoritesInteractor.isInFavorites(vacancyId)
        }
    }

    fun shareUrl() {
        val currentState = _state.value

        if (currentState is VacancyDetailsState.Content) {
            viewModelScope.launch {
                val shareUrl = currentState.vacancy.shareUrl

                if (shareUrl != null) {
                    _events.emit(
                        VacancyDetailsEvent.Share(
                            shareUrl
                        )
                    )
                }
            }
        }
    }
}
