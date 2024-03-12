package ru.practicum.android.diploma.presentation.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.api.SimilarRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.main.SearchingVacancies
import ru.practicum.android.diploma.util.Resource

class VacanciesPagingSource(
    private val searchRepository: SearchRepository?,
    private val params: Map<String, String>?,
    private val similarRepository: SimilarRepository?,
    private val vacancyId: String?,
) : PagingSource<Int, Vacancy>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        try {
            val nextPageNumber = params.key ?: 0
            var response: Resource<SearchingVacancies>? = null

            if (searchRepository != null) {
                val currentParams = this.params!!.toMutableMap()
                currentParams["page"] = nextPageNumber.toString()
                response = searchRepository.vacanciesPagination(currentParams)
            } else if (similarRepository != null) {
                var page: Int = nextPageNumber
                response = similarRepository.similarVacanciesPagination(vacancyId!!, page)
            }

            val (vacancies, pages) = when (response) {
                is Resource.Success -> {
                    (response.data as SearchingVacancies).vacancies to response.data!!.pages
                }

                else -> {
                    emptyList<Vacancy>() to 0
                }
            }

            val nextPage = if (pages - nextPageNumber <= 0) null else nextPageNumber + 1

            return LoadResult.Page(
                data = vacancies,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
