package ru.practicum.android.diploma.domain.impl.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyPage

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {
    override fun getVacancyDetails(vacancyId: String): Flow<Result<VacancyPage>> {
        return repository.getVacancyDetails(vacancyId)
    }
}
