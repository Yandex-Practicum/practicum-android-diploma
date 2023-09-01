package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchRequestOptions
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDto
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider,
) : SearchRepository {
    override fun searchVacancies(query: String): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(SearchRequest(query))
        when (response.resultCode) {
            ERROR -> {
                emit(Resource.Error(resourceProvider.getString(R.string.check_connection)))
            }

            SUCCESS -> {
                with(response as SearchResponse) {

                    val vacanciesList = items.map { mapVacancyFromDto(it) }
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
                    emit(Resource.Error(resourceProvider.getString(R.string.check_connection)))
                }

                SUCCESS -> {
                    with(response as SearchResponse) {
                        val vacanciesList = items.map { mapVacancyFromDto(it) }
                        emit(Resource.Success(vacanciesList))
                        // emit(Resource.Success(mapVacanciesListFromDto(results)))
                    }

                }

                else -> {
                    emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
                }
            }
        }

    /* private fun mapVacanciesListFromDto(list: List<VacancyDto>): List<Vacancy> {
         return list.map {
             Vacancy(
                 it.id, it.name, it.city?="", it.employerName, it.employerLogoUrl,
                 it.salaryCurrency, it.salaryFrom, it.salaryTo
             )
         }
     }*/

    private fun mapVacancyFromDto(vacancyDto: VacancyDto): Vacancy {
        return Vacancy(
            vacancyDto.id,
            vacancyDto.name,
            vacancyDto.area.name,
            vacancyDto.employer.name,
            vacancyDto.employer.vacancies_url ?: "",
            vacancyDto.salary?.currency ?: "",
            vacancyDto.salary?.from ?: 0,
            vacancyDto.salary?.to ?: 0,
        )
    }

   /* private fun mapVacancyListToDto(list: List<Vacancy>): List<VacancyDto> {
        return list.map {
            VacancyDto(
                it.id, it.name, it.city, it.employerName, it.employerLogoUrl,
                it.salaryCurrency, it.salaryFrom, it.salaryTo
            )
        }
    }*/

    companion object {
        const val ERROR = -1
        const val SUCCESS = 200
    }

}