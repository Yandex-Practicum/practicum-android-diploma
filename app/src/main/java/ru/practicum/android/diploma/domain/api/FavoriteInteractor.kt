package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.model.VacancyModel

interface FavoriteInteractor {
    fun getMockResults(): Flow<ArrayList<VacancyModel>>
}
