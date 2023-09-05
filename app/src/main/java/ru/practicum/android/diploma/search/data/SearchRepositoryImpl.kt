package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.data.VacancyDetailsResponse
import ru.practicum.android.diploma.details.domain.models.VacancyDetails
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchRequestDetails
import ru.practicum.android.diploma.search.data.dto.SearchRequestOptions
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDto
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.createValue


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
                    val vacanciesList = items.map { mapVacancyFromDto(it, found) }
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

    override suspend fun loadVacancyDetails(vacancyId: String): Resource<VacancyDetails> {
        val response = networkClient.getVacancyById(SearchRequestDetails(vacancyId))
        when (response.resultCode) {
            ERROR -> {
                return(Resource.Error(resourceProvider.getString(R.string.check_connection)))
            }

            SUCCESS -> {
                with(response as VacancyDetailsResponse) {
                    val data = mapVacancyDetailsFromDto(this)
                    return Resource.Success(data)
                }

            }

            else -> {
                return(Resource.Error(resourceProvider.getString(R.string.server_error)))
            }
        }
    }

    private fun mapVacancyFromDto(vacancyDto: VacancyDto, foundValue: Int): Vacancy {
        return Vacancy(
            vacancyDto.id,
            vacancyDto.name,
            vacancyDto.area.name,
            vacancyDto.employer.name,
            found = foundValue,
            vacancyDto.employer.logo_urls?.original,
            getSymbol(vacancyDto.salary?.currency),
            createValue(vacancyDto.salary?.from),
            createValue(vacancyDto.salary?.to),
        )
    }

    private fun mapVacancyDetailsFromDto(vacancyDetailsResponse: VacancyDetailsResponse): VacancyDetails {
        return VacancyDetails(
            contacts = vacancyDetailsResponse.contacts,
            description = vacancyDetailsResponse.description,
            employer = vacancyDetailsResponse.employer,
            experience = vacancyDetailsResponse.experience,
            key_skills = vacancyDetailsResponse.key_skills,
            schedule = vacancyDetailsResponse.schedule,
        )
    }


    private fun getSymbol(currency: String?): String? {

        return when (currency) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "с"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "UZS"
            else -> null
        }
    }

    companion object {
        const val ERROR = -1
        const val SUCCESS = 200
    }

}