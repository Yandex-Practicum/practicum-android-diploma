package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.vacancy.api.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val vacancyDetailsNetworkDataSource: VacancyDetailsNetworkDataSource,
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): ApiResponse<VacancyDetail> {
        return vacancyDetailsNetworkDataSource.getVacancyDetails(id)
    }
}
