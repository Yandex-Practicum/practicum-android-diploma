package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchVacanciesInteractorImpl(private val repository: SearchVacanciesRepository) :
    SearchVacanciesInteractor {
    override fun searchVacancies(expression: String): Flow<List<Vacancy>> {
        val sanitized = sanitizeText(expression)
        return repository.searchVacancies(sanitized)
            .map { it.vacancies }
    }

    private fun sanitizeText(text: String): String {
        return text.replace(Regex("[^\\p{L}\\p{N}.&\\-'/ ]+"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}
