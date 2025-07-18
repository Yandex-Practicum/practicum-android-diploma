package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesList
import ru.practicum.android.diploma.domain.repositories.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.repositories.SearchVacanciesRepository

class SearchVacanciesInteractorImpl(
    private val repository: SearchVacanciesRepository
) : SearchVacanciesInteractor {

    override fun searchVacancies(textRequest: String, page: Int): Flow<Result<VacanciesList>> {
        return repository.doRequest(textRequest, page)
    }
}
