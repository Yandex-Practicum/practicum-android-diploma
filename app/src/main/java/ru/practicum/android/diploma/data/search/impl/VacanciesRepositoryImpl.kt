package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.SalaryDto
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.models.SearchParams
import java.io.IOException
import java.util.Locale

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {

    override fun getVacancies(searchParams: SearchParams): Flow<List<Vacancy>> {
        return flow {
            val response = networkClient.doRequest(VacancySearchRequest(searchParams))
            when (response.resultCode) {
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as VacancySearchResponse) {
                        val listOfFoundedVacancies = items.map {
                            Vacancy(
                                it.id,
                                it.name,
                                it.area.name,
                                getCorrectFormOfSalaryText(it.salary),
                                it.employer.name,
                                it.employer.logoUrls?.original
                            )
                        }
                        emit(listOfFoundedVacancies)
                    }
                }
                RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE -> {
                    throw HttpException(Response.error<Any>(404, ResponseBody.create(null, "Not Found")))
                }
                RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE -> {
                    throw HttpException(Response.error<Any>(500, ResponseBody.create(null, "Server Error")))
                }
                RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE -> {
                    throw HttpException(Response.error<Any>(400, ResponseBody.create(null, "Bad Request")))
                }
                RetrofitNetworkClient.HTTP_CODE_0 -> {
                    throw HttpException(Response.error<Any>(0, ResponseBody.create(null, "Unknown Error")))
                }
                -1 -> {
                    throw IOException("Network Error")
                }
                else -> {
                    throw HttpException(Response.error<Any>(response.resultCode, ResponseBody.create(null, "Unexpected Error: ${response.resultCode}")))
                }
            }
        }
    }

    private fun getCorrectFormOfSalaryText(salary: SalaryDto?): String? {
        return if (salary == null) {
            null
        } else {
            if (salary.from == salary.to) {
                String.format(Locale.getDefault(), "%d %s", salary.to, getCurrencySymbolByCode(salary.currency!!))
            } else if (salary.to == null) {
                String.format(Locale.getDefault(), "от %d %s", salary.from, getCurrencySymbolByCode(salary.currency!!))
            } else {
                String.format(
                    Locale.getDefault(),
                    "от %d до %d %s",
                    salary.from,
                    salary.to,
                    getCurrencySymbolByCode(salary.currency!!)
                )
            }
        }
    }

    private fun getCurrencySymbolByCode(code: String): String {
        return when (code) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "⃀"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "Soʻm"
            else -> ""
        }
    }

}
