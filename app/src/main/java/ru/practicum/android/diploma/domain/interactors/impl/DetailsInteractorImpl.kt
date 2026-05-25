package ru.practicum.android.diploma.domain.interactors.impl

import ru.practicum.android.diploma.domain.api.DetailsRepository
import ru.practicum.android.diploma.domain.interactors.DetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class DetailsInteractorImpl(
    private val repository: DetailsRepository
) : DetailsInteractor {

    override suspend fun getVacancyDetails(id: String): Resource<VacancyDetail> {
        return repository.getVacancyDetails(id)
    }

}
