package ru.practicum.android.diploma.data.search.impl

import android.content.Context
import androidx.core.content.ContextCompat.getString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
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
    override fun searchVacancies(text: String, page: Int): Flow<Resource<List<DomainVacancy>>> = flow {
        val response = networkClient.getVacancies(VacancyRequest(text, page).map())
        if (response.isSuccessful) {
            with(response.body()) {
                currentPage = this?.page
                foundItems = this?.total
                val data = this?.items?.let { converter.map(it) }
                this@SearchRepositoryImpl.pages = this?.pages
                emit(Resource.Success(data))
            }
        } else {
            emit(Resource.Error(getString(context, R.string.no_internet_connection)))
        }
    }
}
