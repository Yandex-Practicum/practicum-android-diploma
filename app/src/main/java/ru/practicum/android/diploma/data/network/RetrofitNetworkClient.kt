package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.filter.CountryResponse
import ru.practicum.android.diploma.data.dto.filter.IndustryResponse
import ru.practicum.android.diploma.domain.models.filter.Filters
import ru.practicum.android.diploma.util.PER_PAGE


class RetrofitNetworkClient(private val api: ApiService, private val context: Context) :
    NetworkClient {
    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doSearchRequest(text: String, filters: Filters, pageCount: Int): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                var area: String? = null
                if (filters.area != null) area = filters.area.id else if (filters.country != null)
                    area = filters.country.id
                var industriesId: MutableList<String>? = mutableListOf()
                if (!filters.industries.isNullOrEmpty()){
                    for (item in filters.industries) industriesId?.add(item.id)
                } else industriesId = null
                val salary = if(!filters.preferSalary.isNullOrEmpty()) filters.preferSalary else null
                val response =
                    api.search(text, pageCount.toString(), PER_PAGE.toString(), area, industriesId,
                      salary , if (filters.isIncludeSalary) true else null)
                Log.d("RetrofitNetworkClient", "Response: $response")
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doDetailRequest(id: String): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVacancy(id)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doSearchSimilarRequest(id: String): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = api.searchSimilar(id)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doAreaRequest(id: String): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getRegionInfo(id)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doCountryRequest(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val countries = api.getCountres()
                if (countries.isEmpty()) {
                    Response().apply { resultCode = 204 }
                } else {
                    CountryResponse(countries).apply { resultCode = 200 }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doIndustryRequest(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val industries = api.getIndustries()
                if (industries.isEmpty()) {
                    Response().apply { resultCode = 204 }
                } else {
                    IndustryResponse(industries).apply { resultCode = 200 }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
