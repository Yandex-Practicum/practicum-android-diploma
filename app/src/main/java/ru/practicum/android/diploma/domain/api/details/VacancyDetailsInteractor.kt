package ru.practicum.android.diploma.domain.api.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyDetailsInteractor {
    fun getVacancyDetails(vacancyId: String): Flow<Result<Vacancy>>
}
