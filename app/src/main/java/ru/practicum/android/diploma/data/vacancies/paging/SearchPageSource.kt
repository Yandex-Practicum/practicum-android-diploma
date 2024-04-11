package ru.practicum.android.diploma.data.vacancies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.Filters
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse
import java.net.SocketTimeoutException

class SearchPageSource(
    private val vacancySearchRepository: VacanciesSearchRepository,
    private val query: String,
    private val filters: Filters,
) : PagingSource<Int, Vacancy>() {
    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        return try {
            val page: Int = params.key ?: 1
            var response: Pair<VacancyResponse?, String?>? = null
            vacancySearchRepository.getVacancies(query, page, filters).collect { response = it }
            if (response?.first != null) {
                val vacancyResponse = response?.first as VacancyResponse

                val nextKey = if (vacancyResponse.pages > vacancyResponse.page) page + 1 else null
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(vacancyResponse.items, prevKey, nextKey)

            } else {
                LoadResult.Error(Exception("No internet"))
            }
        } catch (e: SocketTimeoutException) {
            LoadResult.Error(e)
        }

    }
}
