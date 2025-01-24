package ru.practicum.android.diploma.data.vacancy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.data.network.RetrofitClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.api.VacancyRepository

class VacancyRepositoryImpl(
    private val vacanciesConverter: VacanciesConverter,
) : VacancyRepository {

    override fun getVacancy(id: String): Flow<ApiResponse<Vacancy>> = flow {
        when (val response = RetrofitClient.doRequestVacancy(id)) {
            null -> {
                emit(ApiResponse.Error())
            }

            else -> {
                val data = vacanciesConverter.convertFull(response)
                emit(ApiResponse.Success(data = data))
            }
        }
    }
}
