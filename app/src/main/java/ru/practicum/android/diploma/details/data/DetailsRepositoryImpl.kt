package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.data.network.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.request.Request
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.flatMap
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val apiHelper: AlternativeRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val converter: VacancyModelConverter,
    private val logger: Logger
) : DetailsRepository {

    @Suppress("UNCHECKED_CAST")
    override suspend fun getFullVacancyInfo(id: String): Either<Failure, VacancyFullInfo> {
        val isFavorite = localDataSource.showIfInFavouriteById(id).firstOrNull() ?: false

        return if (isFavorite) {
            logger.log("getFullVacancyInfo", "getFullVacancyInfo: LOADED FROM CACHE = $id")
            val vacancy = localDataSource.getFavoritesById(id).firstOrNull()
            if (vacancy != null) {
                Either.Right(vacancy)
            } else {
                Either.Left(Failure.NotFound())
            }
        } else {
            ((apiHelper.doRequest(
                Request.VacancyDetailsRequest(id)
            )) as Either<Failure, VacancyFullInfoModelDto>).flatMap {
                logger.log(thisName, "getVacancyFullInfo: FOUND = ${it.id}")
                Either.Right(converter.mapDetails(it))
            }
        }
    }

    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return localDataSource.removeVacancyFromFavorite(id)
    }

    override suspend fun addVacancyToFavorite(vacancy: VacancyFullInfo): Flow<Unit> {
        return localDataSource.addVacancyToFavorite(vacancy)
    }

    override suspend fun showIfInFavourite(id: String): Flow<Boolean> {
        return localDataSource.showIfInFavouriteById(id)
    }

}
