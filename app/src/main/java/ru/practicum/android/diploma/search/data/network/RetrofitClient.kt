package ru.practicum.android.diploma.search.data.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.mapTracksToVacancies
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController
) : NetworkClient {
    override suspend fun doRequest(any: Any): Flow<FetchResult> {
        val response = hhApiService.search(any as String)
        return flowOf(FetchResult.Success(data = mapTracksToVacancies(response.results.toList())))
    }
}