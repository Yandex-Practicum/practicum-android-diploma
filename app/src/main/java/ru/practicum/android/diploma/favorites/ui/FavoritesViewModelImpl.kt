package ru.practicum.android.diploma.favorites.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.vacancy.domain.models.Contacts
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

class FavoritesViewModelImpl(val interactor: FavoritesInteractor) : FavoritesViewModel() {
    private val _state = MutableStateFlow<FavoritesViewState>(FavoritesViewState.Empty)
    override var state: StateFlow<FavoritesViewState> = _state.asStateFlow()
    init {
        // TODO
        // удалить после реализации FavoritesViewModelImpl
        // пример запроса избранных
        viewModelScope.launch {
            interactor.add(
                VacancyDetails(
                    "",
                    "Test",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    emptyList(),
                    Contacts("", "", emptyList()),
                    ""
                )
            )
            interactor.getAll().collect {
                Log.d("Favorites", it.toString())
            }

        }
    }
}
