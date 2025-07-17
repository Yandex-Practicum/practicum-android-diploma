package ru.practicum.android.diploma.domain.vacancysearchscreen.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.paging.VacanciesResult
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.util.Resource

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun search(text: String, page: Int): Flow<Resource<VacanciesResult>> = repository.search(text, page)
    override fun clearCache() {
        repository.clearLoadedPages()
    }
    override fun getVacancyDetailsById(id: String): Flow<Resource<VacancyDetails>> =
        repository.getVacancyDetailsById(id)
}
