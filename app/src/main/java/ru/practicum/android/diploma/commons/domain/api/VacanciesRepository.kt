package ru.practicum.android.diploma.commons.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commons.domain.model.VacanciesModel
import ru.practicum.android.diploma.commons.domain.model.VacancyModel

interface VacanciesRepository {

    fun searchVacancies(
        name: String,
        page: Long,
        amount: Long
    ): Flow<Pair<VacanciesModel, Int>>

    fun searchSimilarVacancies(
        id: String,
        page: Long,
        amount: Long
    ): Flow<Pair<VacanciesModel, Int>>

    fun searchConcreteVacancy(
        id: String
    ): Flow<Pair<VacancyModel?, Int>>

}
