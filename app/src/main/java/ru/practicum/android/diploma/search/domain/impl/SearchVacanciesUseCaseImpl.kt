package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class SearchVacanciesUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : SearchVacanciesUseCase {
    override suspend fun invoke(
        query: String,
        page: Int,
        filter: SelectedFilter?,
    ): Either<Failure, Vacancies> {
        
        val queryMap: Map<String, String> = buildMap {
            put("text", query)
            put("page", page.toString() )
            put("per_page", COUNT_ITEMS)
            
            if (filter?.region?.id != null) { put("area", filter.region.id) }
            else filter?.country?.id?.let { put("area", it) }
           
            filter?.industry?.id?.let { put("industry", it) }
            filter?.salary?.let { put("salary", it) }
            filter?.onlyWithSalary?.let { put("only_with_salary", it.toString()) }
        }
        return repository.searchVacancies(queryMap)
    }
    
    companion object {
        private const val COUNT_ITEMS = "20"
    }
}