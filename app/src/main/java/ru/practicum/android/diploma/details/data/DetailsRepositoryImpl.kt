package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.flow.firstOrNull
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.data.network.RemoteDataSource
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val logger: Logger
) : DetailsRepository {

    override suspend fun getFullVacancyInfo(id: String): Either<Failure, VacancyFullInfo> {

        /** Проверяем есть ли в базе данных, если нет, то идём в сеть */
        val isFavorite = localDataSource.showIfInFavouriteById(id).firstOrNull() ?: false

        return if (!isFavorite) {
            @Suppress("UNCHECKED_CAST")
            remoteDataSource.getVacancyFullInfo(id).fold(
                {
                    logger.log(thisName, "getFullVacancyInfo: ERROR = $it")
                    Either.Left(it)
                }
            ) {
                logger.log(thisName, "getFullVacancyInfo: LOADED FROM INTERNET = $it")
                Either.Right(it)
            } as Either<Failure, VacancyFullInfo>
        } else {
            logger.log(thisName, "getFullVacancyInfo: LOADED FROM CACHE = $id")
            val vacancy = localDataSource.getFavoritesById(id).firstOrNull()
            if (vacancy != null) {
                Either.Right(vacancy)
            } else {
                Either.Left(Failure.NotFound())
            }
        }
    }

    override suspend fun getSimilarVacancies(id: String): Either<Failure, List<Vacancy>> {
        @Suppress("UNCHECKED_CAST")
        return remoteDataSource.getSimilarVacancies(id).fold(
            {
                logger.log(thisName, "getSimilarVacancies: ERROR = $it")
                Either.Left(it)
            }
        ) {
            logger.log(thisName, "getSimilarVacancies: LOADED FROM INTERNET = $it")
            Either.Right(it)
        } as Either<Failure, List<Vacancy>>
    }
}
