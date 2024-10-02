package ru.practicum.android.diploma.search.data.impl

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.search.data.network.HeadHunterAPI
import ru.practicum.android.diploma.search.data.network.NetworkUtils
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams
import java.io.IOException

class SearchRepositoryImpl(
    private val api: HeadHunterAPI,
    private val context: Context
) : SearchRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun searchVacancies(params: VacancySearchParams): Flow<Resource<List<Vacancy>>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                emit(Resource.Loading())

                val options = params.toMap()

                val response = api.searchVacancies(
                    authToken = null,
                    userAgent = "userAgent",
                    options = options
                )

                if (response.items.isNotEmpty()) {
                    val vacancies = response.items.map { vacancy ->
                        Vacancy(
                            id = vacancy.id,
                            name = vacancy.name,
                            salary = vacancy.salary?.from,
                            employer = vacancy.employer,
                        )
                    }

                    emit(
                        Resource.Success(
                            data = vacancies,
                            found = response.found,
                            page = response.page,
                            pages = response.pages
                        )
                    )
                } else {
                    emit(Resource.Error("No vacancies found"))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Network error: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(Resource.Error("HTTP error: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emit(Resource.Error("Network not available"))
        }
    }.flowOn(Dispatchers.IO)
}
