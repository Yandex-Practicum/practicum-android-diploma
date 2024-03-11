package ru.practicum.android.diploma.data.vacancylist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancy
import ru.practicum.android.diploma.data.network.JobVacancySearchApi
import ru.practicum.android.diploma.data.vacancylist.dto.SimilarVacanciesRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.api.SimilarRepository
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

class SimilarRepositoryImpl(
    private val networkClient: NetworkClient,
    private val api: JobVacancySearchApi
) : SimilarRepository {

    override fun searchSimilarVacancies(vacancyId: String, page: Int): Flow<Resource<SearchingVacancies>> = flow {
        val response = networkClient.doRequest(SimilarVacanciesRequest(vacancyId, page))

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val result = response as VacanciesSearchResponse
                    val vacancies = result.items?.map {
                        it.toVacancy()
                    }
                    if (vacancies != null) {
                        val searchingVacancies = SearchingVacancies(
                            vacancies = vacancies,
                            pages = result.found,
                            page = result.page,
                            foundedVacancies = result.found
                        )
                        emit(Resource.Success(searchingVacancies))
                    } else {
                        emit(Resource.Error(response.resultCode.code))
                    }
                } catch (e: Throwable) {
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }

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
