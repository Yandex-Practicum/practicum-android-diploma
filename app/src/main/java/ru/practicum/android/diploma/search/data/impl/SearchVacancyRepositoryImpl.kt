package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.converters.SearchVacancyNetworkConverter
import ru.practicum.android.diploma.search.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class SearchVacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: SearchVacancyNetworkConverter
) : SearchVacancyRepository {

    override fun getVacancyList(query: HashMap<String, String>): Flow<Resource<List<VacancySearch>>> = flow {
        val response = networkClient.doRequest(VacancySearchRequest(query))
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> Resource.Success(
                    (response as VacancySearchResponse).items.map {
                        converter.map(it)
                    }
                )

                else -> Resource.Error("Error")
            }
        )
    }

    override fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequest(vacancyId)
        when (response.resultCode) {
            HttpStatusCode.OK ->
                with(response as VacancyDetailsResponse) {
                    val data = (
                        Vacancy(
                            id,
                            name,
                            salary?.currency.toString(),
                            salary?.from.toString(),
                            employer?.logoUrls?.size240.toString(),
                            area.name,
                            address?.city,
                            experience?.name,
                            schedule?.name,
                            employment?.name,
                            description,
                            keySkills.toString(),
                            isFavorite = false
                        )
                        )
                    emit(Resource.Success(data))
                }
            else -> emit(Resource.Error("Ошибка"))
        }
    }
}
