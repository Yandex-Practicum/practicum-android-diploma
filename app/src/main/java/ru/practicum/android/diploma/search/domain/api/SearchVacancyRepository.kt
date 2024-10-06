package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

interface SearchVacancyRepository {

    fun getVacancyList(query: HashMap<String, String>): Flow<Resource<List<VacancySearch>>>
    fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>>
}
