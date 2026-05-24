package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse

class VacancyDetailInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository,
) : VacancyDetailInteractor {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        if (id.isEmpty()) {
            return GetVacancyDetailsResponse.Error
        }
        return vacancyDetailsRepository.getVacancyDetails(id)
    }
}
