package ru.practicum.android.diploma.vacancy.details.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource

interface VacancyDetailsInteractor {
    fun getVacancyById(
        id: String,
        source: VacancyDetailsSource
    ): Flow<Result<VacancyDetail>>
}
