package ru.practicum.android.diploma.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.response.JobResponse
import ru.practicum.android.diploma.data.network.JobSearchRequest
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.domain.models.Vacancy
import java.io.IOException

class JobPageSource(
    private val networkClient: NetworkClient,
    private val query: String
) : PagingSource<Int, Vacancy>() {

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        return try {
            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize

            val response = withContext(Dispatchers.IO) {
                networkClient.doRequest(JobSearchRequest(query, page, pageSize))
            }

            when (response.resultCode) {
                SUCCESS_RESULT_CODE -> {
                    val vacancies = parseResponse(response)
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (vacancies.isNotEmpty()) page + 1 else null

                    LoadResult.Page(vacancies, prevKey, nextKey)
                }

                else -> LoadResult.Error(Exception("Error loading data"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private fun parseResponse(response: Response): List<Vacancy> {
        return try {
            when (response.resultCode) {
                BAD_REQUEST_RESULT_CODE -> emptyList()
                NOT_SUCCESS_RESULT_CODE -> (response as JobResponse).results
                else -> emptyList()
            }
        } catch (e: IOException) {
            throw IllegalStateException("IOException while parsing response", e)
        }
    }

    companion object {
        private const val SUCCESS_RESULT_CODE = 200
        private const val NOT_SUCCESS_RESULT_CODE = 200
        private const val BAD_REQUEST_RESULT_CODE = -1
    }
}
