package ru.practicum.android.diploma.data.dto.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.request.AllRegionsRequest
import ru.practicum.android.diploma.data.dto.request.CountriesRequest
import ru.practicum.android.diploma.data.dto.request.CountryRegionsRequest
import ru.practicum.android.diploma.data.dto.request.IndustriesRequest
import ru.practicum.android.diploma.data.dto.request.VacancyRequest
import ru.practicum.android.diploma.data.dto.request.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.response.CountriesResponse
import ru.practicum.android.diploma.data.dto.response.IndustriesResponse
import ru.practicum.android.diploma.data.dto.response.RegionResponse
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.domain.NetworkClient
import java.io.IOException

class RetrofitNetworkClient(
    private val connectivityManager: ConnectivityManager,
    private val hhService: HhApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response(INTERNET_NOT_CONNECT)
        }
        return when (dto) {
            is VacancySearchRequest -> getSearchVacancy(dto)
            is VacancyRequest -> getFullVacancy(dto)
            is CountriesRequest -> CountriesResponse(hhService.getCountries())
            is AllRegionsRequest -> RegionResponse(hhService.getAllRegions())
            is CountryRegionsRequest -> getCountryRegions(countryId = dto.countryId)
            is  IndustriesRequest -> getFullIndustries(dto)
            else -> {
                return Response().apply { code = HTTP_BAD_REQUEST_CODE
                }
            }
        }
    }

    private suspend fun getCountryRegions(countryId: String): Response {
        return withContext(Dispatchers.IO) {
            try {
                hhService.getCountryRegions(countryId).apply { code = HTTP_OK_CODE }
            } catch (e: HttpException) {
                Log.e("some error in html", "$e")
                Response().apply { code = HTTP_CODE_0 }
            } catch (e: IOException) {
                Log.e("error in fetching country regions", "$e")
                Response().apply { code = HTTP_INTERNAL_SERVER_ERROR_CODE }
            }
        }
    }

    private suspend fun getSearchVacancy(request: VacancySearchRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                hhService
                    .getVacancies(
                        request.searchParams.searchQuery,
                        request.searchParams.nameOfCityForFilter,
                        request.searchParams.nameOfIndustryForFilter,
                        request.searchParams.onlyWithSalary,
                        request.searchParams.currencyOfSalary,
                        request.searchParams.expectedSalary,
                        request.searchParams.numberOfVacanciesOnPage,
                        request.searchParams.numberOfPage
                    )
                    .apply {
                        code = HTTP_OK_CODE
                    }
            } catch (e: HttpException) {
                when (e.code()) {
                    HTTP_PAGE_NOT_FOUND_CODE -> Response().apply { code = HTTP_PAGE_NOT_FOUND_CODE }
                    else -> Response().apply { code = HTTP_CODE_0 }
                }
            } catch (e: IOException) {
                Log.e("errorSearchVacancy", "$e")
                Response().apply { code = HTTP_INTERNAL_SERVER_ERROR_CODE }
            }
        }
    }

    private suspend fun getFullVacancy(request: VacancyRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                VacancyResponse(hhService.getVacancyById(request.id))
                    .apply { code = HTTP_OK_CODE }
            } catch (e: HttpException) {
                Response().apply { code = e.code() }

            } catch (e: IOException) {
                Log.e("errorFullVacancy", "$e")
                Response().apply { code = HTTP_INTERNAL_SERVER_ERROR_CODE }
            }
        }
    }

    private suspend fun getFullIndustries(request: IndustriesRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                IndustriesResponse(hhService.getAllIndustries())
                    .apply { code = HTTP_OK_CODE }
            } catch (e: HttpException) {
                Response().apply { code = e.code() }

            } catch (e: IOException) {
                Log.e("errorFullIndustries", "$e")
                Response().apply { code = HTTP_INTERNAL_SERVER_ERROR_CODE }
            }
        }
    }

    private fun isConnected(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            false
        }
    }

    // возможно стоит вынести в отдельный object?
    companion object {
        const val HTTP_BAD_REQUEST_CODE = 400
        const val HTTP_OK_CODE = 200
        const val HTTP_INTERNAL_SERVER_ERROR_CODE = 500
        const val HTTP_PAGE_NOT_FOUND_CODE = 404
        const val HTTP_CODE_0 = 0
        const val INTERNET_NOT_CONNECT = -1
    }
}
