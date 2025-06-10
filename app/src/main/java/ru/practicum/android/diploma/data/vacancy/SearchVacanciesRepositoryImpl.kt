package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.domain.vacancy.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail

class SearchVacanciesRepositoryImpl(
    private val searchVacanciesNetworkDataSource: SearchVacanciesNetworkDataSource,
) : SearchVacanciesRepository {
    override suspend fun search(text: String): Result<List<VacancyDetail>> {
        val result = kotlin.runCatching {
            searchVacanciesNetworkDataSource.getSearchResults(text)
        }

        if (result.isFailure) {
            // Например, отправить событие об ошибке в аналитику
        }

        return result
    }
}
