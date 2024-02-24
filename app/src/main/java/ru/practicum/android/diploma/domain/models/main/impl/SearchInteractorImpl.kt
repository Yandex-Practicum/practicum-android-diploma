package ru.practicum.android.diploma.domain.models.main.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.SearchInteractor
import ru.practicum.android.diploma.domain.models.main.Vacancy

class SearchInteractorImpl(
    val repository: SearchRepository
) : SearchInteractor {
    override suspend fun searchTrack(query: String): List<Vacancy> {
        return withContext(Dispatchers.IO) {
            repository.getVacancyByQuery(query)
        }
    }
}
