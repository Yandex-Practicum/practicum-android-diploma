package ru.practicum.android.diploma.domain.api.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.details.VacancyDetailStatus

interface VacancyDetailsRepository {
    fun getVacancyDetails(vacancyId: String): Flow<VacancyDetailStatus<Vacancy>>
}
