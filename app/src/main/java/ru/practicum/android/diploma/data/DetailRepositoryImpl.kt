package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.DetailRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class DetailRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider,
    private val mapper: VacancyMapper,
) : DetailRepository {
    override fun getVacancy(id: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequest(DetailRequest(id))
        when (response.resultCode) {
            ERROR -> {
                emit(Resource.Error(resourceProvider.getString(R.string.check_connection)))
            }

            SUCCESS -> {
                with(response as VacancyResponse) {
                    val vacancy = mapper.mapVacancyFromFullVacancyDto(this.vacancy)
                    emit(Resource.Success(vacancy))
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

