package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.domain.model.Vacancy
import ru.practicum.android.diploma.domain.repository.VacancyRepository

class DataRepository(private val apiService: ApiService) : VacancyRepository {

    override suspend fun getVacancies(): List<Vacancy> {
        val dtoList = apiService.getVacancies()
        return dtoList.map { dto ->
            Vacancy(
                id = dto.id,
                title = dto.title,
                description = dto.description
            )
        }
    }
}
