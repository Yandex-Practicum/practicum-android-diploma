package ru.practicum.android.diploma.data.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Page
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val appDatabase: AppDatabase,
    private val gson: Gson
) : VacancyRepository {
    companion object {
        private const val SUCCESSFUL_REQUEST = 200
    }

    override fun getVacancies(options: Map<String, String>): Flow<Resource<Page>> = flow {
        val request = Request.VacanciesRequest(options)
        val response = networkClient.doRequest(request) as VacanciesResponse
        val result = if (response.resultCode == SUCCESSFUL_REQUEST) {
            Resource.Success(
                Page(
                    response.items.map { convertFromVacancyDto(it) },
                    response.page,
                    response.pages,
                    response.found
                )
            )
        } else {
            Resource.Error("${response.resultCode}")
        }
        emit(result)
    }

    override suspend fun getVacancy(vacancyId: String): Resource<Vacancy> {
        val result: Resource<Vacancy>
        val vacancy = appDatabase.getFavoriteVacancyDao().getVacancyById(vacancyId)
        if (vacancy != null) {
            result = Resource.Success(vacancy.toDomain())
        } else {
            val request = Request.VacancyRequest(vacancyId)
            val response = networkClient.doRequest(request) as VacancyResponse
            result = if (response.resultCode == SUCCESSFUL_REQUEST) {
                Resource.Success(convertFromVacancyDto(response.vacancy))
            } else {
                Resource.Error("${response.resultCode}")
            }
        }
        return result
    }

    private fun convertFromVacancyDto(vacancy: VacancyDto): Vacancy {
        val salary = if (vacancy.salary != null) {
            Salary(
                vacancy.salary.currency, vacancy.salary.from, vacancy.salary.to
            )
        } else {
            null
        }
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.employer.logoUrls?.url90,
            vacancy.area.name,
            salary,
            vacancy.employer.name,
            vacancy.employment?.name,
            vacancy.experience?.name,
            vacancy.description,
            vacancy.alternateUrl
        )
    }

    private fun FavoriteVacancyEntity.toDomain(): Vacancy {
        return Vacancy(
            id = this.id,
            name = this.name,
            logoUrl90 = this.logoUrl,
            area = this.area,
            salary = this.salary?.let { gson.fromJson(it, object : TypeToken<Salary>() {}.type) },
            employerName = this.employerName,
            description = this.description,
            alternateUrl = this.alternateUrl,
            employment = this.employment,
            experience = this.experience,
            isFavorite = true

        )
    }
}
