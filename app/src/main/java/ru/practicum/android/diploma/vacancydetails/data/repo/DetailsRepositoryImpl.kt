package ru.practicum.android.diploma.vacancydetails.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.data.NetworkClient
import ru.practicum.android.diploma.common.domain.Resource
import ru.practicum.android.diploma.vacancydetails.data.dto.DetailsRequest
import ru.practicum.android.diploma.vacancydetails.data.dto.DetailsResponse
import ru.practicum.android.diploma.vacancydetails.domain.api.DetailsRepository
import ru.practicum.android.diploma.vacancydetails.presentation.models.Vacancy

class DetailsRepositoryImpl(private val networkClient: NetworkClient) : DetailsRepository {
    override fun getVacansy(expression: String, page: Int?, perPage: Int?): Flow<Resource<List<Vacancy>>> = flow {
        when (val response = networkClient.doRequest(DetailsRequest(expression, page, perPage))) {
            is DetailsResponse -> {
                emit(
                    Resource.Success(
                        response.items.map {
                            Vacancy(
                                id = it.id,
                                name = it.name,
                                region = it.area.toString(),
                                city = it.address?.city,
                                companyId = it.employer.id.toString(),
                                companyName = it.employer.name,
                                companyLogo = null, // добавить значение
                                salaryFrom = it.salary.from,
                                salaryTo = it.salary.to,
                                employment = it.name,
                                experience = it.experience.name,
                                salaryCurrency = it.salary.currency
                            )
                        }
                    )
                )
            } else -> {
                emit(Resource.Error(response.errorType))
            }
        }
    }

}
