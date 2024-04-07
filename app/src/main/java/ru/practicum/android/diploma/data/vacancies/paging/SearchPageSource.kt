package ru.practicum.android.diploma.data.vacancies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

class SearchPageSource(
    private val vacancySearchRepository: VacanciesSearchRepository,
    private val query: String,
) : PagingSource<Int, Vacancy>() {
    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), null, null)
        }
        val page: Int = params.key ?: 1

        var response: Pair<VacancyResponse?, String?>? = null
        vacancySearchRepository.getVacancies(query, page).collect { response = it }

        if (response?.first != null) {
            val responseVacancy = (response?.first as VacancyResponse)
            if (responseVacancy.items.isEmpty()) {
                return LoadResult.Error(Exception("Empty"))
                //stateLiveData.postValue(SearchViewState.EmptyVacancies)
            } else {
                val vacancyResponse = (response?.first as VacancyResponse)

                var nextKey = if (vacancyResponse.pages > vacancyResponse.page) page + 1 else null
                val prevKey = if (page == 1) null else page - 1
                return LoadResult.Page(vacancyResponse.items, prevKey, nextKey)
                //stateLiveData.postValue(SearchViewState.Content(vacancyResponse.items, vacancyResponse.found))
            }

        } else {
            return LoadResult.Error(Exception(response?.second))
            //stateLiveData.postValue(SearchViewState.NoInternet)
        }


    }
}
