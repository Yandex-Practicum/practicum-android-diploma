import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.dto.response.JobResponse

import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.data.search.network.JobSearchRequest
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {

    override fun searchJobs(query: String, pageSize: Int): PagingSource<Int, Vacancy> {
        return object : PagingSource<Int, Vacancy>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
                try {
                    val page = params.key ?: 1
                    val response = networkClient.doRequest(JobSearchRequest(query, page, pageSize))

                    return when (response.resultCode) {
                        SUCCESS_RESULT_CODE -> {
                            val vacancies = (response as JobResponse).results
                            LoadResult.Page(
                                data = vacancies,
                                prevKey = if (page > 1) page - 1 else null,
                                nextKey = if (vacancies.isNotEmpty()) page + 1 else null
                            )
                        }
                        else -> LoadResult.Error(Exception("Error loading data"))
                    }
                } catch (e: Exception) {
                    return LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey
                }
            }
        }
    }
    companion object {
        const val SUCCESS_RESULT_CODE = 200
    }
}
