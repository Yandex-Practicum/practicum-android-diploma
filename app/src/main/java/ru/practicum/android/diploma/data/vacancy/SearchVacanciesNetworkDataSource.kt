package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.vacancy.models.VacancyDto
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail

class SearchVacanciesNetworkDataSource(
    private val hhApi: HhApi,
) {
    suspend fun getSearchResults(text: String): List<VacancyDetail> {
        val result = hhApi.searchVacancies(text)

        return result.items.map { formatVacancy(it) }
    }

    private fun formatVacancy(data: VacancyDto): VacancyDetail {
        return VacancyDetail(
            id = data.id,
            name = data.name,
        )
    }
}
