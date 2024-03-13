package ru.practicum.android.diploma.data.vacancylist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancy
import ru.practicum.android.diploma.data.network.JobVacancySearchApi
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val api: JobVacancySearchApi
) : SearchRepository {

    override suspend fun vacanciesPagination(params: Map<String, String>): Resource<SearchingVacancies> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getFullListVacancy(params)
                Resource.Success(SearchingVacancies(
                    response.items?.map { it.toVacancy() } ?: emptyList(),
                    pages = response.pages,
                    page = response.page,
                    foundedVacancies = response.found,
                ))
            } catch (e: Exception) {
                if(e is RuntimeException){
                    Resource.ServerError(500)
                }
                else{Resource.Error(0)}
            }
        }
    }
}
