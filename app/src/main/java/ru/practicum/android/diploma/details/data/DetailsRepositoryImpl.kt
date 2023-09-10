package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val logger: Logger
) : DetailsRepository {
    
    private val latestVacancyFullInfoMutex = Mutex()
    private var latestVacancyFullInfo: VacancyFullInfo? = null
    
    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return localDataSource.removeVacancyFromFavorite(id)
    }
    
    override suspend fun addVacancyToFavorite(vacancy: VacancyFullInfo): Flow<Unit> {
        return localDataSource.addVacancyToFavorite(vacancy)
    }

    override suspend fun getFavoriteVacancy(id: String): Flow<Boolean> {
        return localDataSource.getFavoriteVacancyById(id)
    }

    override suspend fun getFullVacancyInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>> = flow {
        if (latestVacancyFullInfo?.id != id) {
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
            logger.log(
                thisName,
                "getFullVacancyInfo: LOADED FROM CACHE = $latestVacancyFullInfo"
            )
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
