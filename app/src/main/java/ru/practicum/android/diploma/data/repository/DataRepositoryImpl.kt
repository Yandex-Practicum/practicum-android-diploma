package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.DataRepository
import ru.practicum.android.diploma.util.Resource
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
            Resource.Error("Проверьте подключение к интернету").also {
                e.printStackTrace() // Логируем исключение вместо проглатывания
            }
        } catch (e: Exception) {
            Resource.Error("Неизвестная ошибка: ${e.message}").also {
                e.printStackTrace() // Логируем исключение вместо проглатывания
            }
        }
    }

    private fun handleSuccessResponse(body: VacancySearchResponse?): Resource<SearchResult> {
        return if (body != null) {
            val vacancies = mapVacancies(body.items)
            createSuccessResult(body, vacancies)
        } else {
            Resource.Error("Пустой ответ от сервера")
        }
    }

    private fun mapVacancies(items: List<VacancyDto>): List<Vacancy> {
        return items.map { dto ->
            Vacancy(
                id = dto.id,
                title = dto.name,
                salary = mapSalary(dto.salary),
                employer = mapEmployer(dto.employer),
                area = mapArea(dto.area),
                description = dto.description ?: "Описание не указано"
            )
        }
    }

    private fun mapSalary(salaryDto: SalaryDto?) = salaryDto?.let { dto ->
        ru.practicum.android.diploma.domain.models.Salary(
            from = dto.from,
            to = dto.to,
            currency = dto.currency
        )
    }

    private fun mapEmployer(employerDto: EmployerDto?) = employerDto?.let { dto ->
        ru.practicum.android.diploma.domain.models.Employer(
            id = dto.id,
            name = dto.name,
            logoUrl = dto.logo
        )
    }

    private fun mapArea(areaDto: AreaDto?) = areaDto?.let { dto ->
        ru.practicum.android.diploma.domain.models.Area(
            id = dto.id,
            name = dto.name
        )
    }

    private fun createSuccessResult(
        body: VacancySearchResponse,
        vacancies: List<Vacancy>
    ): Resource<SearchResult> {
        return Resource.Success(
            SearchResult(
                found = body.found,
                pages = body.pages,
                page = body.page,
                vacancies = vacancies
            )
        )
    }
}
