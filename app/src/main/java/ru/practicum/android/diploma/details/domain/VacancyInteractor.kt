package ru.practicum.android.diploma.details.domain

import ru.practicum.android.diploma.details.domain.models.VacancyDetails

interface VacancyInteractor {

    suspend fun loadVacancyDetails(vacancyId: String): Pair<VacancyDetails?, String?>
}