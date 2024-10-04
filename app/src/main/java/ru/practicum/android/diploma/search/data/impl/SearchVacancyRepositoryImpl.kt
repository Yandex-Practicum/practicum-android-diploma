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

class SearchVacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: SearchVacancyNetworkConverter
) : SearchVacancyRepository {

    override fun getVacancyList(
        page: Int,
        text: String,
        industry: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Flow<Resource<List<VacancySearch>>> = flow {
        val response = networkClient.doRequest(VacancySearchRequest(text)) // обсудить и доработать VacancySearchRequest
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK.code -> Resource.Success(
                        (response as VacancySearchResponse).items.map {
                            converter.map(it)
                        }
                )
                else -> Resource.Error("Error")
            }
        )
    }

}
