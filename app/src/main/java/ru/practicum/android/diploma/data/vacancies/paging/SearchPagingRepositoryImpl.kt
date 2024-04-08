package ru.practicum.android.diploma.data.vacancies.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.search.SearchPagingRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class SearchPagingRepositoryImpl(private val vacanciesSearchRepository: VacanciesSearchRepository) :
    SearchPagingRepository {
    override fun getSearchPaging(query: String): Flow<PagingData<Vacancy>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20),
            // Тадаааам, вот такая вот фабрика (её нет)
            pagingSourceFactory = { SearchPageSource(vacanciesSearchRepository, query) }
        ).flow
    }
}
