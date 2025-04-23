package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.main.VacancyLong
import ru.practicum.android.diploma.domain.models.main.VacancyShort

interface SearchVacancyInteractor {
    fun searchVacancy(vacancyName: String): Flow<Resource<List<VacancyShort>>>
    fun searchVacancyDetails(vacancyId: String): Flow<Pair<VacancyLong?, String?>>
}
