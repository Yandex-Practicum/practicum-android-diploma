package ru.practicum.android.diploma.domain.similar

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SimilarRepository {
    fun searchVacancies(vacancyId: String): Flow<Resource<List<Vacancy>>>
}