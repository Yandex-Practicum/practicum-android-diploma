package ru.practicum.android.diploma.domain.detailsvacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails

interface DetailsVacancyInteractor {
    suspend fun doRequest(vacancyId: String): Result<VacancyDetails>
}
