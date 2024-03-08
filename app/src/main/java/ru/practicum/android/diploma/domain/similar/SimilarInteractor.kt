package ru.practicum.android.diploma.domain.similar

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies

interface SimilarInteractor {

    fun searchSimilarVacancies(vacancyId: String): Flow<Pair<SearchingVacancies?, Int?>>
}
