package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.vacancy.models.VacancyDto
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

class SearchVacanciesNetworkDataSource(
    private val hhApi: HhApi,
) {
    suspend fun getSearchResults(text: String): List<Vacancy> {
        val result = hhApi.searchVacancies(text)

        return result.items.map { formatVacancy(it) }
    }

    private fun formatVacancy(data: VacancyDto): Vacancy {
        return Vacancy(
            id = data.id,
            name = data.name,
        )
    }
}
