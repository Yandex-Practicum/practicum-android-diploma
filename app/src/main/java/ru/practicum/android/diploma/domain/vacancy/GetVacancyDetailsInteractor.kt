package ru.practicum.android.diploma.domain.vacancy

import retrofit2.HttpException
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import java.io.IOException

class GetVacancyDetailsInteractor(private val repository: VacancyRepository) {

    suspend fun execute(vacancyId: String): Result<DomainVacancy> {
        return try {
            val response = repository.getVacancyDetails(vacancyId)
            if (response.resultCode == SUCCESS_CODE) {
                response.data?.let { Result.success(it) } ?: Result.failure(Exception("Invalid data format"))
            } else {
                Result.failure(Exception(response.errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: HttpException) {
            Result.failure(Exception("HTTP error: ${e.message}"))
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
