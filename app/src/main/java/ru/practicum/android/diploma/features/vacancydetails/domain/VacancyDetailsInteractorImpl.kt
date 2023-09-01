package ru.practicum.android.diploma.features.vacancydetails.domain

import ru.practicum.android.diploma.features.vacancydetails.domain.models.VacancyDetails
import ru.practicum.android.diploma.root.domain.VacancyRepository
import ru.practicum.android.diploma.root.data.Outcome


class VacancyDetailsInteractorImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyDetailsInteractor {

    override suspend fun getVacancyById(id: String): Outcome<VacancyDetails> {
        return vacancyRepository.getVacancyById(id)
    }

}