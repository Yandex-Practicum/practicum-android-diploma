package ru.practicum.android.diploma.domain.vacancydetails.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyDetailsRepository {
    fun searchVacancyById(vacancyId: String): Flow<Resource<Vacancy>>
}
