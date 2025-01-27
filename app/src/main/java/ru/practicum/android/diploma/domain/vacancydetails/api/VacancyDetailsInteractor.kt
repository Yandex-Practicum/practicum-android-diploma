package ru.practicum.android.diploma.domain.vacancydetails.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyDetailsInteractor {
    fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>>
}
