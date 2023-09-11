package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.request.Request
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.flatMap
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val converter: VacancyModelConverter,
    private val logger: Logger,
    private val apiHelper: AlternativeRemoteDataSource
) : SearchRepository {
    
    @Suppress("UNCHECKED_CAST")
    @NewResponse
    override suspend fun searchVacancies(query: String, page: String): Either<Failure, Vacancies> {
        return ((apiHelper.doRequest(
            Request.VacanciesRequest(
                query, page
            )
        )) as Either<Failure, VacanciesResponse>).flatMap {
            if (it.found == 0) {
                logger.log(thisName, "searchVacancies: NOTHING FOUND")
                Either.Left(Failure.NotFound())
            } else {
                logger.log(thisName, "searchVacancies: FOUND = ${it.found}")
                Either.Right(converter.vacanciesResponseToVacancies(it))
            }
        }
    }
}