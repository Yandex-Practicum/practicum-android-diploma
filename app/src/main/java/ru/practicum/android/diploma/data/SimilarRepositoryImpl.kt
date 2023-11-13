package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.similar.SearchSimilarResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.similar.SimilarRepository
import ru.practicum.android.diploma.util.Resource

class SimilarRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider,
    private val mapper: VacancyMapper,
) : SimilarRepository {
    override fun searchVacancies(vacancyId: String): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doSearchSimilarRequest(vacancyId)
        when (response.resultCode) {
            ERROR -> {
                emit(Resource.Error(resourceProvider.getString(R.string.check_connection)))
            }

            SUCCESS -> {
                with(response as SearchSimilarResponse) {
                    val vacanciesList = items.map { mapper.mapVacancyFromDto(it, found) }
                    emit(Resource.Success(vacanciesList))
                }

            }

            else -> {
                emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
            }
        }
    }


    companion object {
        const val ERROR = -1
        const val SUCCESS = 200
    }

}