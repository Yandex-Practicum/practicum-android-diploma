package ru.practicum.android.diploma.data.repository

import android.content.Context
import androidx.core.content.ContextCompat.getString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: HeadHunterNetworkClient,
    private val context: Context,
    private val converter: VacancyResponseToDomainMapper,
    private val appDatabase: AppDatabase
) : SearchRepository {
    override var currentPage: Int? = null
    override var foundItems: Int? = null
    override var pages: Int? = null
    override fun searchVacancies(text: String, page: Int): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.getVacancies(VacancyRequest(text, page).map())
        if (response.isSuccessful) {
            with(response as VacancyResponse) {
                currentPage = response.page
                foundItems = response.total
                val data = converter.map(response.items)
                this@SearchRepositoryImpl.pages = response.pages
                emit(Resource.Success(data))
            }
        } else {
            emit(Resource.Error(getString(context, R.string.no_internet_connection)))
        }
    }

    override suspend fun getDetails(id: String): Resource<Vacancy> {
        val response = networkClient.getVacancy(id)
        return if (response.isSuccessful) {
            val favList = appDatabase.favoriteVacancyDao().getFavoriteIds()
            val vacancyResponse = response as VacancyDetails
            val vacancy = converter.map(vacancyResponse, vacancyResponse.id in favList)
            Resource.Success(vacancy)
        } else {
            Resource.Error("Ошибка ${response.code()}")
        }
    }
}
