package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.detail.GetVacancyDetailsResponse

class VacancyDetailInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository,
): VacancyDetailsInteractor {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        if (id.isEmpty()) {
            return GetVacancyDetailsResponse.Error
        }
        return vacancyDetailsRepository.getVacancyDetails(id)
    }
}
