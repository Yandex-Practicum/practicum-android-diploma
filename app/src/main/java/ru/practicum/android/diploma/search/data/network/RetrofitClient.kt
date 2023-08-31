package ru.practicum.android.diploma.search.data.network


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.search.domain.models.mapTracksToVacancies
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val hhApiService: HhApiService,
    private val internetController: InternetController,
    private val logger: Logger,
) : NetworkClient {
    
    override suspend fun doRequest(any: Any): Flow<FetchResult> {
    
        logger.log(thisName, "doRequest -> ++++++++++++++++++++++ ")
       
            val responce = hhApiService.getToken("HH7ME252A40VTFGU6FEM5KCKBL8AT6F916CCUAMC5AJGG3KRT7TR85QQUNCUGHRD",
                "NQ2ERE0BAK3M49S3SG82K6V4TJHJERCE2JNJQT0O0FVEU8HLTVVDU1LSRT6KR66V",
                "client_credentials" ).execute()
    
            logger.log(thisName, "doRequest -> ${responce} ")
        
      
        
        
        
        return flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
        
     /*
        logger.log(thisName, "doRequest -> ${any::class} ")
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
            logger.log(thisName, "doRequest -> $query ")
            val response = hhApiService.search(query)
            logger.log(thisName, "doRequest -> ${response.results.toList()} ")
            return flowOf(FetchResult.Success(data = mapTracksToVacancies(response.results.toList()))) */
        }
    }
