package ru.practicum.android.diploma.vacancy.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {

    override fun getVacancyById(
        id: String,
        source: VacancyDetailsSource
    ): Flow<Result<VacancyDetail>> {
        return when (source) {
            VacancyDetailsSource.SEARCH ->
                repository.getDetailsFromApi(id)
            VacancyDetailsSource.FAVORITES ->
                repository.getDetailsFromDataBase(id)
        }
    }
}
