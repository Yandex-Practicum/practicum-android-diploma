package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.practicum.android.diploma.search.data.filtersbd.FiltersPreferences
import ru.practicum.android.diploma.search.data.network.RetrofitClient
import ru.practicum.android.diploma.search.domain.api.FiltersRepository
import ru.practicum.android.diploma.search.domain.model.FailureType
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.domain.model.Resource
import ru.practicum.android.diploma.util.InternetConnectionChecker
import java.io.IOException

class FiltersRepositoryImpl(
    private val retrofitClient: RetrofitClient,
    private val internetConnectionChecker: InternetConnectionChecker,
    private val filtersPreferences: FiltersPreferences
) : FiltersRepository {

    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        if (!internetConnectionChecker.isInternetAvailable()) {
            emit(Resource.Failed(FailureType.NoInternet))
        } else {
            try {
                val response = retrofitClient.hhApi.getIndustries()
                val industries = response.flatMap { industryResponse ->
                    industryResponse.industries.map { industryDto ->
                        Industry(id = industryDto.id, name = industryDto.name)
                    }
                }
                emit(Resource.Success(industries.sortedBy { it.name }))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Failed(FailureType.ApiError))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Failed(FailureType.NoInternet))
            }
        }
    }

    override fun saveFilters(industry: Industry?, salary: String?, onlyWithSalary: Boolean) {
        filtersPreferences.saveIndustry(industry)
        filtersPreferences.saveSalary(salary)
        filtersPreferences.saveOnlyWithSalary(onlyWithSalary)
    }

    override fun getSavedFilters(): Triple<Industry?, String?, Boolean> {
        return Triple(
            first = filtersPreferences.getIndustry(),
            second = filtersPreferences.getSalary(),
            third = filtersPreferences.getOnlyWithSalary()
        )
    }

    override fun clearSavedFilters() {
        filtersPreferences.clearFilters()
    }
}
