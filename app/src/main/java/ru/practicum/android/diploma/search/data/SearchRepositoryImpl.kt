package ru.practicum.android.diploma.search.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.formatters.CurrencyFormatter
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.Request
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.SearchResult
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.data.dto.SearchResponseDto
import ru.practicum.android.diploma.search.domain.api.SearchRepository

class SearchRepositoryImpl(private val networkClient: NetworkClient, private val context: Context) : SearchRepository {

    override fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        filters: Filters
    ): Flow<Resource<SearchResult>> = flow {
        val params = buildMap {
            put(PARAM_QUERY, query)
            put(PARAM_PAGE, page.toString())
            put(PARAM_PER_PAGE, perPage.toString())

            mapFilters(filters).forEach { (key, value) ->
                if (value.isNotBlank()) {
                    put(key, value)
                }
            }
        }

        emit(Resource.Loading)

        val response = networkClient.doRequest(Request.SearchRequest(params))

        when (response.resultCode) {
            ResultCode.SUCCESS -> {
                val dto = response.data as SearchResponseDto
                val searchResult = mapping(dto)
                emit(Resource.Success(searchResult))
            }
            else -> {
                emit(Resource.Error(
                    code = response.resultCode
                ))
            }
        }
    }

    private fun mapping(dto: SearchResponseDto): SearchResult {
        val vacancies = dto.items.map { vacancyDto ->
            Vacancy(
                id = vacancyDto.id,
                name = listOfNotNull(vacancyDto.name, vacancyDto.city?.takeIf { it.isNotBlank() }).joinToString(","),
                employerName = vacancyDto.company ?: "",
                salary = CurrencyFormatter(context)
                    .format(
                        from = vacancyDto.salary?.from,
                        to = vacancyDto.salary?.to,
                        currency = vacancyDto.salary?.currency
                    ),
                employerLogoUrl = vacancyDto.logo
            )
        }

        return SearchResult(
            items = vacancies,
            found = dto.found,
            page = dto.page,
            pages = dto.pages
        )
    }

    private fun mapFilters(filters: Filters): Map<String, String> {
        var result = mutableMapOf<String, String>()
        filters.area?.let {
            result["area"] = it.id
        }
        filters.industry?.let {
            result["industry"] = it.id
        }
        filters.salary?.let {
            result["salary"] = it
        }
        if (filters.onlyWithSalary) {
            result["only_with_salary"] = "true"
        }
        return result
    }

    companion object {
        private const val PARAM_QUERY = "text"
        private const val PARAM_PAGE = "page"
        private const val PARAM_PER_PAGE = "per_page"
    }
}
