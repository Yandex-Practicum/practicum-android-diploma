package ru.practicum.android.diploma.ui.search.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.Constant.STATIC_PAGE_SIZE
import ru.practicum.android.diploma.data.Constant.SUCCESS_RESULT_CODE
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyData
import java.net.ConnectException

class SearchPage(
    private val query: String,
    private val search: suspend (String, Int) -> Resource<VacancyData>,
) : PagingSource<Int, Vacancy>() {

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        val anchorPosition = state.anchorPosition
        if (anchorPosition != null) {
            val page = state.closestPageToPosition(anchorPosition)
            if (page != null) {
                return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page: Int = params.key ?: 0
        val pageSize: Int = STATIC_PAGE_SIZE
        val response = search(query, page)
        return if (response.data?.listVacancy.isNullOrEmpty().not()) {
            if (response.code == SUCCESS_RESULT_CODE) {
                val data = response.data!!.listVacancy
                val nextKey = if (data.size < pageSize) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                LoadResult.Page(data, prevKey, nextKey)
            } else {
                LoadResult.Error(ConnectException())
            }
        } else {
            LoadResult.Error(NullPointerException())
        }
    }
}
