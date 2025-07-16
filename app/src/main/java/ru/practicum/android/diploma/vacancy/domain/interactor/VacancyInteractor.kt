package ru.practicum.android.diploma.vacancy.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyInteractor(
    private val vacancyRepository: VacancyRepository
) {
    fun getVacancyDetails(id: String): Flow<Resource<VacancyDetails>> {
        return vacancyRepository.getVacancyDetails(id)
    }
}
