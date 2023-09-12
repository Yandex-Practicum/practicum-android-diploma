package ru.practicum.android.diploma.details.data.network

import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface RemoteDataSource {
    suspend fun getVacancyFullInfo(id: String): Either<Failure, Any>
    suspend fun getSimilarVacancies(id: String): Either<Failure, Any>
}