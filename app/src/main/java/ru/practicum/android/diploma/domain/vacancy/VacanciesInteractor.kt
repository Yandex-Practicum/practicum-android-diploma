package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail
import ru.practicum.android.diploma.domain.vacancy.models.VacanciesWithPage

interface VacanciesInteractor {
    fun searchVacancies(
        searchText: String,
        area: String,
        industry: String,
        currency: String,
        salary: Int?,
        onlyWithSalary: Boolean,
        page: Int
    ): Flow<Pair<VacanciesWithPage?, String?>>

    fun getVacancy(vacancyId: String): Flow<Pair<VacancyDetail?, String?>>
}
