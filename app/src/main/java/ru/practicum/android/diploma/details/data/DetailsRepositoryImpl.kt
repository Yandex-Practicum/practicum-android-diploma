package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.data.network.RemoteDataSource
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val logger: Logger
) : DetailsRepository {
    
    private val latestVacancyFullInfoMutex = Mutex()
    private var latestVacancyFullInfo: VacancyFullInfo? = null

    override suspend fun getFullVacancyInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>> = flow {

        /** Проверяем есть ли в базе данных, если нет, то идём в сеть */
        val isFavorite = localDataSource.showIfInFavouriteById(id).firstOrNull() ?: false

        if (latestVacancyFullInfo?.id != id && !isFavorite) {
           remoteDataSource
                .getVacancyFullInfo(id)
                .collect {
                    latestVacancyFullInfoMutex.withLock {
                        logger.log(
                            thisName,
                            "getFullVacancyInfo: LOADED FROM INTERNET = ${it}"
                        )
                        emit (it)
                    }
                }
            
        } else {
            logger.log(thisName, "getFullVacancyInfo: LOADED FROM CACHE = $latestVacancyFullInfo")
            val vacancy = localDataSource.getFavoritesById(id).firstOrNull()
            if (vacancy != null) {
                latestVacancyFullInfoMutex.withLock {
                    latestVacancyFullInfo = vacancy
                }
                emit(NetworkResponse.Success(vacancy))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSimilarVacancies(id: String): Flow<NetworkResponse<List<Vacancy>>> = flow {
        remoteDataSource
            .getSimilarVacancies(id)
            .collect {
                logger.log(thisName, "getSimilarVacancies: LOADED FROM INTERNET = ${it}")
                emit(it)
            }
    }.flowOn(Dispatchers.IO)
}
