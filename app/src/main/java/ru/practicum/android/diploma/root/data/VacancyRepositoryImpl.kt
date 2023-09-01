package ru.practicum.android.diploma.root.data

import com.google.gson.Gson
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsMapper
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsRequest
import ru.practicum.android.diploma.features.vacancydetails.domain.models.VacancyDetails
import ru.practicum.android.diploma.root.data.network.NetworkSearch
import ru.practicum.android.diploma.root.data.network.models.NetworkResultCode
import ru.practicum.android.diploma.root.domain.VacancyRepository

class VacancyRepositoryImpl(
    private val detailsMapper: VacancyDetailsMapper,
    private val networkClient: NetworkSearch,
    val gson: Gson
) : VacancyRepository {
    override suspend fun getVacancyById(id: String): Outcome<VacancyDetails> {
        val response = networkClient.getVacancyById(dto = VacancyDetailsRequest(id))

        return when (response.resultCode) {

            NetworkResultCode.SUCCESS -> {
                if (response.data != null) {
                    val vacancyDetails = detailsMapper(response.data!!)
                    Outcome.Success(data = vacancyDetails)
                } else {
                    Outcome.Error(status = NetworkResultCode.SERVER_ERROR, data = null)
                }
            }

            NetworkResultCode.CONNECTION_ERROR -> {
                Outcome.Error(status = NetworkResultCode.CONNECTION_ERROR, data = null)
            }

            else -> {
                Outcome.Error(status = NetworkResultCode.UNKNOWN_ERROR, data = null)
            }
        }
    }
}