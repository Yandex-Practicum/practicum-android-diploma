package ru.practicum.android.diploma.details.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.data.network.RemoteDataSource
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {
    private val latestFullVacancyFullInfoMutex = Mutex()
    private var latestFullVacancyFullInfo: Vacancy? = null
    override suspend fun removeVacancyFromFavorite(id: Long): Flow<Int> {
        return  localDataSource.removeVacancyFromFavorite(id)
    }
    override suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Long> {
        return localDataSource.addVacancyToFavorite(vacancy)
    }
    override suspend fun getFullVacancyInfo(id: Long): Flow<FetchResult> {
       if (latestFullVacancyFullInfo?.id != id) {
            remoteDataSource.getVacancyFullInfo(id).collect {
                latestFullVacancyFullInfoMutex.withLock {
                    Log.e(thisName, "getFullVacancyInfo: LOADED FROM INTERNET = ${it.data?.first()}", )
                    this.latestFullVacancyFullInfo = it.data?.first()
                }
            }
        }else{
           Log.e(thisName, "getFullVacancyInfo: LOADED FROM CACHE = $latestFullVacancyFullInfo", )
       }
        return latestFullVacancyFullInfoMutex.withLock {
            flowOf(FetchResult.Success(listOf(this.latestFullVacancyFullInfo!!)))
        }
    }
}
