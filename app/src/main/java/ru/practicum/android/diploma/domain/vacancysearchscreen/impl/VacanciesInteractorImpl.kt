package ru.practicum.android.diploma.domain.vacancysearchscreen.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.util.Resource

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {
    override fun search(text: String): Flow<Resource<List<Vacancy>>> = flow {
        val vacanciesList = repository.search(text).first()
        if (vacanciesList == null) {
            emit(Resource.Error(""))
        } else {
            emit(Resource.Success(vacanciesList))
        }
    }

    override fun getVacancyDetailsById(id: String): Flow<Pair<VacancyDetails?, String?>> {
        return repository.getVacancyDetailsById(id).map { result ->
            when(result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
                else -> throw IllegalStateException("Unexpected Resource type: $result")
            }
        }
    }
}
