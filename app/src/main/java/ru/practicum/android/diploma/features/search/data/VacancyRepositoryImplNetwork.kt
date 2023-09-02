package ru.practicum.android.diploma.features.search.data

import ru.practicum.android.diploma.features.search.domain.model.Filter
import ru.practicum.android.diploma.features.search.domain.model.QueryError
import ru.practicum.android.diploma.features.search.domain.model.ResponseModel
import ru.practicum.android.diploma.features.search.domain.repository.VacancyRepository

class VacancyRepositoryImplNetwork: VacancyRepository {
    override suspend fun loadVacancies(
        value: String,
        filterParams: Filter
    ): ResponseModel {//MOCK!
        return ResponseModel(QueryError.SOMETHING_WENT_WRONG,ArrayList())
    }
}