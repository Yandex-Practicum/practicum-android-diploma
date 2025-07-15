package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.VacancyPreview

interface SearchInteractor {
    fun getVacancies(text: String, area: String?): Flow<Pair<List<VacancyPreview>?, FailureType?>>
}
