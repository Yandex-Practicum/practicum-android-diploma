package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.main.VacancyLong
import ru.practicum.android.diploma.domain.models.main.VacancyShort

interface SearchVacancyInteractor {
    fun searchVacancy(vacancyName: String): Flow<Pair<List<VacancyShort>?, String?>>
    fun searchVacancyDetails(vacancyId: String): Flow<Pair<VacancyLong?, String?>>
}
