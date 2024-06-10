package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyResponse

class HeadHunterRetrofitNetworkClient(private val api: HeadHunterApi) : HeadHunterNetworkClient {
    companion object {
        private const val AUTHORIZATION_HEADER = "Bearer APPLRO2GGE350U5M54G5SAOIA52SMR6DH2RDCT4AH2I6O59JEOE6GETL8R0QJE2J"
    }

    override suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse> {
        return api.getVacancies(AUTHORIZATION_HEADER, filters)
    }

    override suspend fun getVacancy(id: String): Response<VacancyDetails> {
        return api.getVacancyDetails(AUTHORIZATION_HEADER, id)
    }
}
