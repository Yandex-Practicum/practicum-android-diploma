package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.detail.FullVacancy


interface FavouriteInteractor {
    fun addToFavourite(fullVacancy: FullVacancy): Flow<Boolean>

    fun getFavoriteList(): Flow<List<ru.practicum.android.diploma.domain.models.Vacancy>>

}