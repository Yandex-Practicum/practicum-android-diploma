package ru.practicum.android.diploma.ui.search.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchPage(
    val search: suspend (expression: String, page: Int) -> Resource<List<Vacancy>>,
    private val query: String
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
        return if (response.data != null) {
            val nextKey = if (response.data!!.size < pageSize) null else page + 1
            val prevKey = if (page == 0) null else page - 1
            LoadResult.Page(response.data!!, prevKey, nextKey)
        } else {
            LoadResult.Error(Exception("Ошибка загрузки"))
        }
    }

    companion object {
        const val STATIC_PAGE_SIZE = 20
    }
}

