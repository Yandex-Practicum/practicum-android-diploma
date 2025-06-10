package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface VacanciesInteractor {
    fun searchVacancies(
        searchText: String,
        area: String,
        industry: String,
        currency: String,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<Resource<List<Vacancy>>>

    fun getVacancy(vacancyId: String): Flow<Resource<VacancyDetail>>
}
