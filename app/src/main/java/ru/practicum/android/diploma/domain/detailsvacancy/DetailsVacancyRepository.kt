package ru.practicum.android.diploma.domain.detailsvacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails

interface DetailsVacancyRepository {
    suspend fun doRequest(vacancyId: String): Result<VacancyDetails>
}
