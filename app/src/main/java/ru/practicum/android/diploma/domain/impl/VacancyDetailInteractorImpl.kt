package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDetailInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository,
) : VacancyDetailInteractor {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        if (id.isEmpty()) {
            return GetVacancyDetailsResponse.Error
        }
        return vacancyDetailsRepository.getVacancyDetails(id)
    }

    override fun getFavoriteVacancies(): Flow<List<VacancyDetail>> {
        return vacancyDetailsRepository.getFavoriteVacancies()
    }

    override fun getFavoriteVacancyById(id: String): Flow<VacancyDetail> {
        return vacancyDetailsRepository.getFavoriteVacancyById(id)
    }

    override suspend fun addVacancyToFavorites(vacancyDetail: VacancyDetail) {
        return vacancyDetailsRepository.addVacancyToFavorites(vacancyDetail)
    }

    override suspend fun deleteVacancyFromFavorites(id: String) {
        return vacancyDetailsRepository.deleteVacancyFromFavorites(id)
    }
}
