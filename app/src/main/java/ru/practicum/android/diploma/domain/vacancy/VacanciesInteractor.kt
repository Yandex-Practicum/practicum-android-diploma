package ru.practicum.android.diploma.domain.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.FilterOptions
import ru.practicum.android.diploma.domain.vacancy.models.VacanciesWithPage
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail

interface VacanciesInteractor {
    fun searchVacancies(filter: FilterOptions): Flow<Pair<VacanciesWithPage?, String?>>

    fun getVacancy(vacancyId: String): Flow<Pair<VacancyDetail?, String?>>
}
