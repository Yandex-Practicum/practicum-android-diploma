package ru.practicum.android.diploma.data.vacancylist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancy
import ru.practicum.android.diploma.data.network.JobVacancySearchApi
import ru.practicum.android.diploma.domain.api.SimilarRepository
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

class SimilarRepositoryImpl(
    private val api: JobVacancySearchApi
) : SimilarRepository {

    override suspend fun similarVacanciesPagination(vacancyId: String, page: Int): Resource<SearchingVacancies> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSimilarVacancies(vacancyId, page)
                Resource.Success(SearchingVacancies(
                    response.items?.map { it.toVacancy() } ?: emptyList(),
                    pages = response.pages,
                    page = response.page,
                    foundedVacancies = response.found
                ))
            } catch (e: Exception) {
                Resource.Error(0)
            }
        }
    }
}
