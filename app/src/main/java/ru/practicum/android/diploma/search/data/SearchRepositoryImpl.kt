package ru.practicum.android.diploma.search.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.Request
import ru.practicum.android.diploma.core.data.network.Resource
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.models.SearchResult
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.data.dto.SearchResponseDto
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import kotlin.collections.listOfNotNull

class SearchRepositoryImpl(private val networkClient: NetworkClient, private val context: Context) : SearchRepository {

    override fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        filters: Map<String, String>
    ): Flow<Resource<SearchResult>> = flow {
        val params = buildMap {
            put(PARAM_QUERY, query)
            put(PARAM_PAGE, page.toString())
            put(PARAM_PER_PAGE, perPage.toString())

            filters.forEach { (key, value) ->
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
            ResultCode.NO_INTERNET -> {
                emit(Resource.Error(
                    message = context.getString(R.string.no_internet_connection),
                    code = response.resultCode
                ))
            }
            ResultCode.SERVER_ERROR -> {
                emit(Resource.Error(
                    message = context.getString(R.string.server_error_please_try_again),
                    code = response.resultCode
                ))
            }
            else -> {
                emit(Resource.Error(
                    message = context.getString(R.string.unknown_error),
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
                salary = buildSalaryString(
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

    private fun buildSalaryString(from: Int?, to: Int?, currency: String?): String {
        val fromStr = context.getString(R.string.from)
        val toStr = context.getString(R.string.to)
        return when {
            from != null && to != null -> "$fromStr $from $toStr $to ${currency ?: ""}".trim()
            from != null -> "$fromStr $from ${currency ?: ""}".trim()
            to != null -> "$toStr $to ${currency ?: ""}".trim()
            else -> context.getString(R.string.salary_empty)
        }
    }

    companion object {
        private const val PARAM_QUERY = "text"
        private const val PARAM_PAGE = "page"
        private const val PARAM_PER_PAGE = "per_page"
    }
}
