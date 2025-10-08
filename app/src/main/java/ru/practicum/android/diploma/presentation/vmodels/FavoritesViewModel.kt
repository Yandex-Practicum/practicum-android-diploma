package ru.practicum.android.diploma.presentation.vmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.root.favorites.models.FavoritesState

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val _favoritesState = MutableLiveData<FavoritesState>(FavoritesState.Idle)
    val favoritesState: LiveData<FavoritesState> = _favoritesState

    private var allFavoritesJob: Job? = null
    private val coroutineError = CoroutineExceptionHandler { _, _ ->
        _favoritesState.postValue(FavoritesState.Error)
    }

    fun getListAllFavorites() {
        allFavoritesJob?.cancel()
        allFavoritesJob = viewModelScope.launch(coroutineError) {
            _favoritesState.postValue(FavoritesState.Loading)
            favoritesInteractor.getAllVacancies().collect { list ->
                if (list.isNotEmpty()) {
                    _favoritesState.postValue(
                        FavoritesState.Complete(
                            list.map {
                                Vacancy(
                                    id = it.id,
                                    title = it.name,
                                    description = it.description,
                                    salary = it.salary,
                                    employer = it.employer,
                                    area = it.area
                                )
                            }
                        )
                    )
                } else {
                    _favoritesState.postValue(
                        FavoritesState.Nothing
                    )
                }
            }
        }
    }
}
