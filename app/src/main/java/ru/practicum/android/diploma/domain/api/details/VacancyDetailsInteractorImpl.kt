package ru.practicum.android.diploma.domain.api.details

import ru.practicum.android.diploma.domain.models.VacancyDetails
import kotlinx.coroutines.flow.Flow

class VacancyDetailsInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository
): VacancyDetailsInteractor {
    override suspend fun getVacancyDetails(id: String): Flow<VacancyDetails> {
        return vacancyDetailsRepository.getVacancyDetails(id)
    }
}
