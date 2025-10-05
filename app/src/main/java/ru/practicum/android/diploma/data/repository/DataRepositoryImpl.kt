package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.DataRepository
import ru.practicum.android.diploma.util.Resource
import java.io.IOException

class DataRepositoryImpl(
    private val api: HhApi
) : DataRepository {

    companion object {
        private const val HTTP_OK = 200
        private const val HTTP_UNAUTHORIZED = 401
        private const val HTTP_FORBIDDEN = 403
        private const val HTTP_NOT_FOUND = 404
        private const val HTTP_SERVER_ERROR = 500
    }

    override suspend fun searchVacancies(query: String, page: Int): Resource<SearchResult> {
        return try {
            val response = api.searchVacancies(query, page)

            when (response.code()) {
                HTTP_OK -> handleSuccessResponse(response.body())
                HTTP_UNAUTHORIZED -> Resource.Error("Ошибка авторизации")
                HTTP_FORBIDDEN -> Resource.Error("Доступ запрещен")
                HTTP_NOT_FOUND -> Resource.Error("Ресурс не найден")
                HTTP_SERVER_ERROR -> Resource.Error("Ошибка сервера")
                else -> Resource.Error("Ошибка: ${response.code()} - ${response.message()}")
            }
        } catch (e: IOException) {
            Resource.Error("Проверьте подключение к интернету")
        } catch (e: Exception) {
            Resource.Error("Неизвестная ошибка: ${e.message}")
        }
    }

    private fun handleSuccessResponse(body: VacancySearchResponse?): Resource<SearchResult> {
        return if (body != null) {
            val vacancies = body.items.map { dto ->
                Vacancy(
                    id = dto.id,
                    title = dto.name,
                    salary = dto.salary?.let { salaryDto ->
                        ru.practicum.android.diploma.domain.models.Salary(
                            from = salaryDto.from,
                            to = salaryDto.to,
                            currency = salaryDto.currency
                        )
                    },
                    employer = dto.employer?.let { employerDto ->
                        ru.practicum.android.diploma.domain.models.Employer(
                            id = employerDto.id,
                            name = employerDto.name,
                            logoUrl = employerDto.logo
                        )
                    },
                    area = dto.area?.let { areaDto ->
                        ru.practicum.android.diploma.domain.models.Area(
                            id = areaDto.id,
                            name = areaDto.name
                        )
                    },
                    description = dto.description ?: "Описание не указано"
                )
            }

            Resource.Success(
                SearchResult(
                    found = body.found,
                    pages = body.pages,
                    page = body.page,
                    vacancies = vacancies
                )
            )
        } else {
            Resource.Error("Пустой ответ от сервера")
        }
    }
}
