package ru.practicum.android.diploma.search.data.network


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.search.domain.models.mapTracksToVacancies
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController
) : NetworkClient {
    override suspend fun doRequest(any: Any): Flow<FetchResult> {
        return if (any !is VacancyRequest ){
            flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
        }else{
            val query = when(any){
                is VacancyRequest.FullInfoRequest ->{
                    any.id.toString()
                }
                is VacancyRequest.SearchVacanciesRequest ->{
                    any.query
                }
            }
            val response = hhApiService.search(query)
            return flowOf(FetchResult.Success(data = mapTracksToVacancies(response.results.toList())))
        }
    }
}