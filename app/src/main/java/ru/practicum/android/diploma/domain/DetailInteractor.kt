package ru.practicum.android.diploma.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface DetailInteractor {
    fun getVacancy(id: String): Flow<Pair<Vacancy?, String?>>

}
