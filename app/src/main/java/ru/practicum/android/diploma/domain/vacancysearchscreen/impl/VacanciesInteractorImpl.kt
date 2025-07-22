package ru.practicum.android.diploma.domain.vacancysearchscreen.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.filters.VacancyFilters
import ru.practicum.android.diploma.domain.models.paging.VacanciesResult
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.util.Resource

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun search(vacancy: VacancyFilters): Flow<Resource<VacanciesResult>> =
        repository.search(vacancy)

    override fun clearCache() {
        repository.clearLoadedPages()
    }
    override fun getVacancyDetailsById(id: String): Flow<Resource<VacancyDetails>> =
        repository.getVacancyDetailsById(id)
}
