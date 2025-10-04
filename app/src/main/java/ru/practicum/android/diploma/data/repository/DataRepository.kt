package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import ru.practicum.android.diploma.util.Resource
import java.io.IOException

class DataRepository(private val apiService: ApiService) : VacancyRepository {

    override suspend fun searchVacancies(query: String, page: Int): Resource<SearchResult> {
        return try {
            val response = apiService.searchVacancies(query, page)

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        Resource.Success(
                            SearchResult(
                                vacancies = body.items.map { dto ->
                                    Vacancy(
                                        id = dto.id.hashCode(), // Конвертируем String ID в Int
                                        title = dto.name, // Используем name вместо title
                                        description = dto.description,
                                        salary = dto.salary?.let { salaryDto ->
                                            Salary(
                                                from = salaryDto.from,
                                                to = salaryDto.to,
                                                currency = salaryDto.currency
                                            )
                                        },
                                        employer = dto.employer?.let { employerDto ->
                                            Employer(
                                                id = employerDto.id,
                                                name = employerDto.name,
                                                logoUrl = employerDto.logo
                                            )
                                        },
                                        area = dto.area?.let { areaDto ->
                                            Area(
                                                id = areaDto.id,
                                                name = areaDto.name
                                            )
                                        }
                                    )
                                },
                                found = body.found,
                                page = body.page,
                                pages = body.pages
                            )
                        )
                    } else {
                        Resource.Error("Пустой ответ от сервера")
                    }
                }
                response.code() == 401 -> {
                    Resource.Error("Ошибка авторизации. Проверьте API токен")
                }
                else -> {
                    Resource.Error("Ошибка сервера: ${response.code()} - ${response.message()}")
                }
            }
        } catch (e: IOException) {
            Resource.Error("Проверьте подключение к интернету")
        } catch (e: Exception) {
            Resource.Error("Произошла ошибка: ${e.message}")
        }
    }

    override suspend fun getVacancies(): List<Vacancy> {
        return emptyList()
    }
}
