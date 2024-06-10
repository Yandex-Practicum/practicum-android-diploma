package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import java.io.IOException

class VacancyRepository(private val networkClient: HeadHunterNetworkClient) {

    suspend fun getVacancies(filters: Map<String, String>): NetworkResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getVacancies(filters)
                if (response.isSuccessful) {
                    val vacancies: List<Vacancy> = response.body()?.items ?: emptyList()
                    NetworkResponse(vacancies, SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(emptyList<Vacancy>(), ERROR_CODE, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(emptyList<Vacancy>(), ERROR_CODE, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(emptyList<Vacancy>(), ERROR_CODE, "HTTP error: ${ex.message}")
            }
        }
    }

    suspend fun getVacancyDetails(id: String): NetworkResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getVacancy(id)
                if (response.isSuccessful) {
                    val vacancyDetails: VacancyDetails = response.body() ?: throw NotFoundException("Vacancy not found")
                    NetworkResponse(vacancyDetails, SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(null, ERROR_CODE, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(null, ERROR_CODE, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(null, ERROR_CODE, "HTTP error: ${ex.message}")
            }
        }
    }

    companion object {
        private const val TOKEN = "Bearer APPLRO2GGE350U5M54G5SAOIA52SMR6DH2RDCT4AH2I6O59JEOE6GETL8R0QJE2J"
        private const val SUCCESS_CODE = 200
        private const val ERROR_CODE = 400
    }
}

data class NetworkResponse(
    val data: Any?,
    val resultCode: Int,
    val errorMessage: String? = null
)

class NotFoundException(message: String) : Exception(message)
