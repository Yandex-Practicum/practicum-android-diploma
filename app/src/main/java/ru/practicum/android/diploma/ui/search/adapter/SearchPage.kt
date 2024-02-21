package ru.practicum.android.diploma.ui.search.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchPage (
    val search: suspend (expression: String, page: Int) -> Resource<List<Vacancy>>,
    private val query: String
) : PagingSource<Int, Vacancy>() {

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page: Int = params.key ?: 0
        val pageSize: Int = 20//params.loadSize
        val response = search (query, page)
        if (response.data != null) {
            //val articles = checkNotNull(response.body().vacancy.map { it.toVacancy() })
            val nextKey = if (response.data!!.size < pageSize) null else page + 1
            val prevKey = if (page == 0) null else page - 1
//            return LoadResult.Page(articles, prevKey, nextKey)
            return LoadResult.Page(response.data!!, prevKey, nextKey)
        } else {
            return LoadResult.Error(Exception("Ошибка загрузки"))
        }
    }


}

