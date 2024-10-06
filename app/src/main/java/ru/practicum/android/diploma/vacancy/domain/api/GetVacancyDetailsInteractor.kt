package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy


interface GetVacancyDetailsInteractor {
    fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>?>
}
