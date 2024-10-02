package ru.practicum.android.diploma.vacancy.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetail

interface VacancyDetailRepository {
    fun listVacancy(id: String): Flow<Resource<VacancyDetail>>
}
