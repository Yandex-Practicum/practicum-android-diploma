package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.data.network.RemoteDataSource
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val logger: Logger
) : DetailsRepository {
    
    private val latestVacancyFullInfoMutex = Mutex()
    private var latestVacancyFullInfo: Vacancy? = null
    
    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return localDataSource.removeVacancyFromFavorite(id)
    }
    
    override suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Unit> {
        return localDataSource.addVacancyToFavorite(vacancy)
    }
    
    override suspend fun getFullVacancyInfo(id: String): Flow<FetchResult> {
        
        if (latestVacancyFullInfo?.id != id) {
            
            remoteDataSource
                .getVacancyFullInfo(id)
                .collect {
                    latestVacancyFullInfoMutex.withLock {
                        
                        logger.log(
                            thisName,
                            "getFullVacancyInfo: LOADED FROM INTERNET = ${it.data?.first()}"
                        )
                        
                        this.latestVacancyFullInfo = it.data?.first()
                    }
                }
            
        } else {
            logger.log(
                thisName,
                "getFullVacancyInfo: LOADED FROM CACHE = $latestVacancyFullInfo"
            )
        }
        return latestVacancyFullInfoMutex.withLock {
            flowOf(FetchResult.Success(listOf(this.latestVacancyFullInfo!!)))
        }
    }
}
