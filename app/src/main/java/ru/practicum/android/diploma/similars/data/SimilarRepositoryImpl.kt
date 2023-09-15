package ru.practicum.android.diploma.similars.data

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.request.Request
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.similars.domain.SimilarRepository
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.flatMap
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SimilarRepositoryImpl @Inject constructor(
    private val logger: Logger,
    private val apiHelper: AlternativeRemoteDataSource,
    private val converter: VacancyModelConverter,
) : SimilarRepository {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getSimilarVacancies(id: String): Either<Failure, List<Vacancy>> {

        return ((apiHelper.doRequest(
            Request.SimilarVacanciesRequest(id)
        )) as Either<Failure, VacanciesSearchCodeResponse>).flatMap {
            if (it.found == 0) {
                logger.log(thisName, "getSimilarVacancies: NOTHING FOUND")
                Either.Left(Failure.NotFound())
            } else {
                logger.log(thisName, "getSimilarVacancies: FOUND = ${it.found}")
                Either.Right(converter.mapList(it.items!!))
            }
        }
    }
}