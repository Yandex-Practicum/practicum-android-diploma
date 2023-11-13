package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.search.SearchRequest
import ru.practicum.android.diploma.data.dto.search.SearchRequestOptions
import ru.practicum.android.diploma.data.dto.search.SearchResponse
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider,
    private val mapper: VacancyMapper,
) : SearchRepository {
    override fun searchVacancies(query: String): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(SearchRequest(query))
        when (response.resultCode) {
            ERROR -> {
                emit(Resource.Error(resourceProvider.getString(R.string.no_internet)))
            }

            SUCCESS -> {
                with(response as SearchResponse) {
                    val vacanciesList = items.map { mapper.mapVacancyFromDto(it, found) }
                    emit(Resource.Success(vacanciesList))
                }

            }

            else -> {
                emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
            }
        }
    }

    override fun getVacancies(options: HashMap<String, String>): Flow<Resource<List<Vacancy>>> =
        flow {
            val response = networkClient.doRequest(SearchRequestOptions(options))
            when (response.resultCode) {
                ERROR -> {
                    emit(Resource.Error(resourceProvider.getString(R.string.no_internet)))
                }

                SUCCESS -> {
                    with(response as SearchResponse) {
                        //   Заготовка для фильтров
                        //    val vacanciesList = items.map { mapVacancyFromDto(it) }
                        //   emit(Resource.Success(vacanciesList,))
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