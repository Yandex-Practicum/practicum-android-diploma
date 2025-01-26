package ru.practicum.android.diploma.domain.vacancydetails.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository

class VacancyDetailsInteractorImpl(
    private val vacancyDetailsRepository: VacancyDetailsRepository
) : VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>> {
        return vacancyDetailsRepository.searchVacancyById(vacancyId)
    }
}
