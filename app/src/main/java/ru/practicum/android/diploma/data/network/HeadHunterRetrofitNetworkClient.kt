package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyResponse

class HeadHunterRetrofitNetworkClient(private val api: HeadHunterApi) : HeadHunterNetworkClient {

    override suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse> {
        return api.getVacancies(filters)
    }

    override suspend fun getVacancy(id: String): Response<VacancyDetails> {
        return api.getVacancyDetails(id)
    }
}
