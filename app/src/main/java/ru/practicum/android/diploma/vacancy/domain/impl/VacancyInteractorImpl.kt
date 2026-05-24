package ru.practicum.android.diploma.vacancy.domain.impl

import ru.practicum.android.diploma.core.data.network.Resource
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

class VacancyInteractorImpl(val repository: VacancyDetailsRepository) : VacancyInteractor {
    override suspend fun getById(id: String): Resource<VacancyDetails> {
        return repository.getById(id)
    }
}
