package ru.practicum.android.diploma.vacancy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.FavoriteVacancyInteractor
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.mock.MockData
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.ui.VacancyState

class VacancyViewModel(
    private val vacancyId: String,
    private val vacancyInteractor: VacancyInteractor,
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val state = _state.asStateFlow()

    init {
        loadVacancy()
    }

    private fun loadVacancy() {
        _state.value = VacancyState.Loading
        viewModelScope.launch {
            vacancyInteractor.getVacancyDetails(vacancyId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val isFavorite = favoriteInteractor.isFavorite(vacancyId)
                        _state.value = VacancyState.Content(result.data!!, isFavorite)
                    }
                    is Resource.Error -> {
                        _state.value = VacancyState.Error(result.message ?: "unknown_error")
                    }
                }
            }
        }
    }

    fun onFavoriteClicked() {
        val currentState = _state.value
        if (currentState !is VacancyState.Content) {
            return
        }
        viewModelScope.launch {
            if (currentState.isFavorite) {
                favoriteInteractor.removeById(vacancyId)
            } else {
                val vacancyToAdd = MockData.getVacancyById(vacancyId)
                if (vacancyToAdd != null) {
                    favoriteInteractor.add(vacancyToAdd)
                }
            }
            _state.value = currentState.copy(isFavorite = !currentState.isFavorite)
        }
    }
}
