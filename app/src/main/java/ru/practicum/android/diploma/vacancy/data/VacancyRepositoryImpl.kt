package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NOT_FOUND
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_SUCCESS
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.vacancy.data.dto.VacancyRequest
import ru.practicum.android.diploma.vacancy.data.dto.VacancyResponse
import ru.practicum.android.diploma.vacancy.data.dto.responseToVacancyFull
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val favouritesRepository: FavouritesRepository,
) : VacancyRepository {

    override fun getVacancy(id: Int): Flow<VacancyFull> = flow {
        val response = networkClient.doRequest(VacancyRequest(id = id))
        when (response.resultCode) {
            RESULT_CODE_SUCCESS -> {
                val vacancyFull = responseToVacancyFull(response as VacancyResponse)
                if (favouritesRepository.getById(id) != null) {
                    favouritesRepository.upsertVacancy(vacancyFull)
                }
                emit(vacancyFull)
            }

            RESULT_CODE_NO_INTERNET -> {
                emit(favouritesRepository.getById(id) ?: VacancyFull())
            }

            RESULT_CODE_NOT_FOUND -> {
                favouritesRepository.deleteVacancy(id)
                emit(VacancyFull())
            }

            else -> emit(VacancyFull())
        }
    }
}
