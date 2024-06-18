package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.data.repository.VacancyRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class GetVacancyDetailsInteractor(private val repository: VacancyRepository) {

    suspend fun execute(vacancyId: String): Result<Vacancy> {
        return try {
            val response = repository.getVacancyDetails(vacancyId)
            if (response.resultCode == 200) {
                response.data?.let { Result.success(it) } ?: Result.failure(Exception("Invalid data format"))
            } else {
                Result.failure(Exception(response.errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
