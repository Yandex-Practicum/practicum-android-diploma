package ru.practicum.android.diploma.search.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun searchVacancies(options: Map<String, String>): Flow<String> = flow {}

    override fun listVacancy(id: String): Flow<String> = flow {}

    override fun listAreas(): Flow<String> = flow {}

    override fun listIndustries(): Flow<String> = flow {}
}
