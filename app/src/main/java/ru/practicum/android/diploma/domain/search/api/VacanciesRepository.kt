package ru.practicum.android.diploma.domain.search.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.VacancyResponse

interface VacanciesRepository {
    fun searchVacancies(text: String?, options: HashMap<String, Int>): Flow<Resource<VacancyResponse>>
}
