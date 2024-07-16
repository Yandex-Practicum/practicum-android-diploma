package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NOT_FOUND
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.utils.ResponseData
import ru.practicum.android.diploma.vacancy.data.dto.VacancyRequest
import ru.practicum.android.diploma.vacancy.data.dto.VacancyResponse
import ru.practicum.android.diploma.vacancy.data.dto.responseToVacancyFull
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val favouritesRepository: FavouritesRepository,
) : VacancyRepository {

    override fun getVacancy(id: Int): Flow<ResponseData<VacancyFull>> = flow {
        when (val response = networkClient.doRequest(VacancyRequest(id))) {
            is VacancyResponse -> {
                val vacancyFull = responseToVacancyFull(response)
                if (favouritesRepository.getById(id) != null) {
                    favouritesRepository.upsertVacancy(vacancyFull)
                }
                emit(ResponseData.Data(vacancyFull))
            }

            else -> {
                when (response.resultCode) {
                    RESULT_CODE_NO_INTERNET -> {
                        if (favouritesRepository.getById(id) != null) {
                            emit(ResponseData.Data(favouritesRepository.getById(id)!!))
                        } else {
                            emit(ResponseData.Error(ResponseData.ResponseError.NO_INTERNET))
                        }
                    }

                    RESULT_CODE_NOT_FOUND -> {
                        favouritesRepository.deleteVacancy(id)
                        emit(ResponseData.Error(ResponseData.ResponseError.NOT_FOUND))
                    }

                    else -> emit(ResponseData.Error(ResponseData.ResponseError.SERVER_ERROR))
                }
            }
        }
    }
}
