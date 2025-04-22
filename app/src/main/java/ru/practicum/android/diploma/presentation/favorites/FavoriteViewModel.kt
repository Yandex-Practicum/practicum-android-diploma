package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.InteractorFavoriteVacancies

class FavoriteViewModel(
    private val interactorFavoriteVacancies: InteractorFavoriteVacancies
) : ViewModel() {
    private val _stateLiveData = MutableLiveData<FavoriteVacanciesState>()
    val stateLiveData: LiveData<FavoriteVacanciesState> get() = _stateLiveData

    init {
        observeFavoriteVacancies()
    }

    private fun observeFavoriteVacancies() {
        viewModelScope.launch {
            interactorFavoriteVacancies
                .getVacanciesFlow()
                .collect { result ->
                    when (result) {
                        is ResponseDb.Loading -> renderState(FavoriteVacanciesState.Loading)
                        is ResponseDb.Error -> renderState(FavoriteVacanciesState.Error)
                        is ResponseDb.Success -> {
                            val vacancies = result.data
                            if (vacancies.isEmpty()) {
                                renderState(FavoriteVacanciesState.Empty)
                            } else {
                                renderState(FavoriteVacanciesState.Content(vacancies))
                            }
                        }
                    }
                }
        }
    }

    private fun renderState(state: FavoriteVacanciesState) {
        _stateLiveData.postValue(state)
    }
}
