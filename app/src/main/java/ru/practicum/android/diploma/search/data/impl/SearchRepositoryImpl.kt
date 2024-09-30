package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams

class SearchRepositoryImpl : SearchRepository {
    override fun searchVacancies(params: VacancySearchParams): Flow<Resource<List<Vacancy>>> {
        TODO("Not yet implemented")
    }
}
