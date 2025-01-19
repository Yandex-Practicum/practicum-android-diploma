package ru.practicum.android.diploma.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractor

class FavoriteScreenViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel()
