package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow

import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchVacanciesInteractorImpl(private val repository: SearchVacanciesRepository) :
    SearchVacanciesInteractor {
    override suspend fun searchVacancies(expression: String): Flow<SearchVacanciesInteractor.Resource<List<Vacancy>>> {
        return repository.searchVacancies(sanitizeText(expression))
    }
    private fun sanitizeText(text: String): String {
        return text.replace(Regex("[^\\p{L}\\p{N}.&\\-'/ ]+"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}
