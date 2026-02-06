package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancySearchFilter
import ru.practicum.android.diploma.domain.models.VacancySearchResult

class SearchVacanciesInteractorImpl(private val repository: SearchVacanciesRepository) :
    SearchVacanciesInteractor {
    override fun searchVacancies(filters: VacancySearchFilter): Flow<VacancySearchResult> {
        val sanitizedFilters = filters.copy(
            text = filters.text?.let { sanitizeText(it) }
        )
        return repository.searchVacancies(sanitizedFilters)
    }

    private fun sanitizeText(text: String): String {
        return text.replace(Regex("[^\\p{L}\\p{N}.&\\-'/ ]+"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}
