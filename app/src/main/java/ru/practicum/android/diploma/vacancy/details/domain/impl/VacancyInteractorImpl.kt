package ru.practicum.android.diploma.vacancy.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.details.domain.repository.VacancyRepository

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {
    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> {
        return repository.getVacancyById(id)
    }
}
