package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchVacanciesInteractor {
    suspend fun searchVacancies(expression: String): Flow<Resource<List<Vacancy>>>

    sealed interface Resource<T> {
        class Success<T>(val data: T) : Resource<T>
        class Error<T>(val errorCode: Int) : Resource<T>
    }
}
