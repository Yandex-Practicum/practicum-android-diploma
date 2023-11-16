package ru.practicum.android.diploma.domain.similar

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SimilarInteractor {
    fun loadVacancies(vacancyId: String): Flow<Pair<List<Vacancy>?, String?>>
}