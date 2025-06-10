package ru.practicum.android.diploma.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface VacanciesRepository {
    fun searchVacancies(options: FilterOptions): Flow<Resource<List<Vacancy>>>
    fun getVacancy(vacancyId: String): Flow<Resource<VacancyDetail>>
}
