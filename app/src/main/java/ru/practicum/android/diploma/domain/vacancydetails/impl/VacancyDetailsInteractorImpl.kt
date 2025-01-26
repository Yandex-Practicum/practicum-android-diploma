package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository

class VacancyDetailsInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository
) : VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: String): Flow<Pair<Vacancy?, String?>> {
        return vacancyDetailsRepository.searchVacancyById(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.value, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
