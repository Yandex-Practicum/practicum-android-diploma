package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDetailInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository,
) : VacancyDetailInteractor {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        if (id.isEmpty()) {
            return GetVacancyDetailsResponse.Error
        }
        return vacancyDetailsRepository.getVacancyDetails(id)
    }

    override fun getVacancyFromFavorites(id: String): Flow<Vacancy?> {
        return vacancyDetailsRepository.getVacancyFromFavorites(id)
    }

    override suspend fun saveVacancyToFavorites(vacancy: Vacancy) {
        vacancyDetailsRepository.saveVacancyToFavorites(vacancy)
    }
}
