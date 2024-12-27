package ru.practicum.android.diploma.domain.vacancy.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {

    override fun getVacancyId(id: String): Flow<Resource<Vacancy>> {
        return repository.getVacancyId(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    Resource.Success(result.data)
                }

                is Resource.Error -> {
                    Resource.Error(result.message)
                }
            }
        }
    }
}
