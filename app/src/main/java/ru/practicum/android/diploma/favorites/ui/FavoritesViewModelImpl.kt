package ru.practicum.android.diploma.favorites.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor

class FavoritesViewModelImpl(val interactor: FavoritesInteractor) : FavoritesViewModel() {
    private val _state = MutableStateFlow<FavoritesViewState>(FavoritesViewState.Empty)
    override var state: StateFlow<FavoritesViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.getAll()
                .catch { _state.value = FavoritesViewState.Error() }
                .collect { vacancies ->
                    _state.value = if (vacancies.isEmpty()) {
                        FavoritesViewState.Empty
                    } else {
                        FavoritesViewState.Data(vacancies)
                    }
                }
        }
    }
}
