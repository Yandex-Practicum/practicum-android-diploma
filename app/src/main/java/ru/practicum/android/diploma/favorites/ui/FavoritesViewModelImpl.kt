package ru.practicum.android.diploma.favorites.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.vacancy.domain.models.Contacts
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

class FavoritesViewModelImpl(val interactor: FavoritesInteractor) : FavoritesViewModel() {
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
