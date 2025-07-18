package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.vacancy.vacanysearch.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.vacancy.vacanysearch.VacancySearchResponseDto
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.VacanciesList
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.domain.repositories.SearchVacanciesRepository
import ru.practicum.android.diploma.util.AppException
import ru.practicum.android.diploma.util.toCurrencySymbol

class SearchVacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchVacanciesRepository {

    override fun doRequest(textRequest: String, page: Int): Flow<Result<VacanciesList>> = flow {
        val response = withContext(Dispatchers.IO) {
            networkClient.vacanciesSearchRequest(createRequest(textRequest, page))
        }

        response
            .onSuccess { data ->
                if (data.vacancies.isEmpty()) {
                    emit(Result.failure(AppException.EmptyResult()))
                } else {
                    emit(Result.success(mapResponse(data)))
                }
            }
            .onFailure { error ->
                emit(Result.failure(error))
            }
    }

    // Пока работает на запрос без фильтров
    override fun createRequest(textRequest: String, page: Int): Map<String, String> {
        val requestWithoutFilters = VacancySearchRequest(
            page = page,
            text = textRequest,
            area = null,
            industry = null,
            salary = null,
            onlyWithSalary = null
        )
        return toQueryMap(requestWithoutFilters)
    }

    // Пока работает на запрос без фильтров
    private fun toQueryMap(vacancySearchRequest: VacancySearchRequest): Map<String, String> =
        buildMap {
            put("page", vacancySearchRequest.page.toString())
            put("per_page", vacancySearchRequest.perPage)
            put("text", vacancySearchRequest.text)
            vacancySearchRequest.area?.let {
                put("area", vacancySearchRequest.area)
            }
            vacancySearchRequest.industry?.let {
                put("industry", vacancySearchRequest.industry)
            }
            vacancySearchRequest.salary?.let {
                put("salary", vacancySearchRequest.salary.toString())
            }
            if (vacancySearchRequest.onlyWithSalary == true) {
                put("only_with_salary", "true")
            }
        }

    private fun mapResponse(dto: VacancySearchResponseDto): VacanciesList {
        return VacanciesList(
            page = dto.page,
            pages = dto.pages,
            found = dto.found,
            vacanciesList = dto.vacancies.map { vacancy ->
                VacanciesPreview(
                    vacancyId = vacancy.id,
                    vacancyName = vacancy.name,
                    employerName = vacancy.employer.name,
                    employerLogo = vacancy.employer.employerLogo?.path,
                    address = when (vacancy.address?.raw) {
                        null -> vacancy.area.name
                        else -> vacancy.address.raw
                    },
                    salaryFrom = vacancy.salary?.from,
                    salaryTo = vacancy.salary?.to,
                    currency = vacancy.salary?.currency.toCurrencySymbol(),
                )
            }
        )
    }
}
