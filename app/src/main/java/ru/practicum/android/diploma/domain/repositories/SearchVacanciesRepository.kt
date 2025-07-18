package ru.practicum.android.diploma.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesList

interface SearchVacanciesRepository {
    fun doRequest(textRequest: String, page: Int): Flow<Result<VacanciesList>>
    fun createRequest(textRequest: String, page: Int): Map<String, String>
}
