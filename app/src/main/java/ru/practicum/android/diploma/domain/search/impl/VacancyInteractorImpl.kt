package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.DetailVacancy
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.VacancyInteractor

class VacancyInteractorImpl(private val repository: SearchRepository) : VacancyInteractor {
    override suspend fun getDetailVacancy(id: String): Flow<Resource<DetailVacancy>> {
        return repository.getDetailVacancy(id)
    }
}
